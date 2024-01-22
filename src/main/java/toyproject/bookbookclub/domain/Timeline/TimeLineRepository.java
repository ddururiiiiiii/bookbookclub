package toyproject.bookbookclub.domain.Timeline;

import org.springframework.stereotype.Repository;
import toyproject.bookbookclub.domain.Members.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TimeLineRepository {

    private static final Map<String, Timeline> store = new ConcurrentHashMap<>(); //static 사용

    public Timeline save(Timeline timeline){
        store.put(timeline.getTimelineId(), timeline);
        return timeline;
    }

    public Timeline findByTimelineId(String timeLineId){
        return store.get(timeLineId);
    }

    public List<Timeline> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(String timelineId, Timeline updateParam){
        Timeline timeline = findByTimelineId(timelineId);
        timeline.setContent(updateParam.getContent());
        timeline.setLastUpdateDate(updateParam.getLastUpdateDate());
    }

    public void clearStore(){
        store.clear();
    }

}
