package seulgi.bookbookclub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seulgi.bookbookclub.domain.Book;
import seulgi.bookbookclub.domain.Follow;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.domain.Timeline;
import seulgi.bookbookclub.repository.BookRepository;
import seulgi.bookbookclub.repository.MemberRepository;
import seulgi.bookbookclub.repository.TimelineRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TimelineService {

    private final TimelineRepository timelineRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final FollowService followService;

    //글 작성
    @Transactional
    public Integer createTimeline(Integer memberSeq, Book book, String contents) {
        Member member = memberRepository.findByMemberSeq(memberSeq);
        bookRepository.save(book);
        Timeline timeline = Timeline.createTimeline(member, book, contents);
        timelineRepository.save(timeline);
        return timeline.getTimelineSeq();
    }

    //글 삭제
    @Transactional
    public void deleteTimeline(Integer timelineSeq){
        Timeline timeline = timelineRepository.findById(timelineSeq);
        if (timeline == null){
            throw new IllegalArgumentException("해당 글이 존재하지 않습니다.");
        }
        timeline.delete();
    }

    //특정회원이 올린 타임라인 조회
    public List<Timeline> getMemberTimelines(Integer memberSeq){
        return timelineRepository.findTimelinesByMember(memberSeq);
    }

    //팔로잉한 회원들의 타임라인 조회
    public List<Timeline> getFollowedTimelines(Integer memberSeq) {
        List<Follow> follows = followService.getFollowings(memberSeq);
        List<Integer> followingSeqs = follows.stream()
                .map(follow -> follow.getFollowing().getMemberSeq())
                .collect(Collectors.toList());
        return timelineRepository.findByFollowers(followingSeqs);
    }

    // 타임라인 ID로 타임라인 단건 조회
    public Timeline getTimelineByTimeLineId(Integer timelineSeq){
         return timelineRepository.findById(timelineSeq);
    }

}
