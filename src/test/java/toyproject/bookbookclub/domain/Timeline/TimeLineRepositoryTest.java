package toyproject.bookbookclub.domain.Timeline;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TimeLineRepositoryTest {

    TimeLineRepository timeLineRepository = new TimeLineRepository();

    @AfterEach
    void afterEach(){ timeLineRepository.clearStore();}

    @Test
    void save(){
        //given
        Timeline timeline = new Timeline("1", "bookid", "bookImg", "memberId", "contnet", "2024-01-01");

        //when
        Timeline saveTimeline = timeLineRepository.save(timeline);

        //then
        //option + enter : assert 단축
        Timeline findTimeline = timeLineRepository.findByTimelineId(timeline.getTimelineId());
        assertThat(findTimeline).isEqualTo(timeline);

    }

    @Test
    void findAll(){

        //gvien
        Timeline timeline1 = new Timeline("1", "bookid1", "bookImg1", "memberId1", "contnet1", "2024-01-01");
        Timeline timeline2 = new Timeline("2", "bookid2", "bookImg2", "memberId2", "contnet2", "2024-01-02");
        timeLineRepository.save(timeline1);
        timeLineRepository.save(timeline2);

        //when
        List<Timeline> result = timeLineRepository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);

    }

    @Test
    void updateTimeline(){
        //given
        //저장할 데이터 생성
        Timeline timeline = new Timeline("1", "bookid1", "bookImg1", "memberId1", "contnet1", "2024-01-01");

        //저장
        Timeline savedTimeline = timeLineRepository.save(timeline);
        //저장된 데이터의 타임라인 아이디 가져옴
        String timelineId = savedTimeline.getTimelineId();

        //when
        //업데이트 할 데이터 생성
        Timeline updateParam = new Timeline("1", "bookid1", "bookImg1", "memberId1", "contnet2", "2024-01-01");

        //업데이트
        timeLineRepository.update(timelineId, updateParam);

        //저장한 데이터의 타임라인 데이터 가져옴
        Timeline findId = timeLineRepository.findByTimelineId(timelineId);

        //then
        Assertions.assertThat(findId.getContent()).isEqualTo(updateParam.getContent());

    }

}