package seulgi.bookbookclub.api;


import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.web.bind.annotation.*;
import seulgi.bookbookclub.domain.Book;
import seulgi.bookbookclub.domain.Timeline;
import seulgi.bookbookclub.dto.CreateTimelineRequest;
import seulgi.bookbookclub.dto.CreateTimelineResponse;
import seulgi.bookbookclub.dto.Result;
import seulgi.bookbookclub.dto.TimelineDto;
import seulgi.bookbookclub.service.TimelineService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timelines")
public class TimelineApiController {

    private final TimelineService timelineService;

    //타임라인 등록
    @PostMapping
    public CreateTimelineResponse saveTimeline(@RequestBody CreateTimelineRequest request){
        Book createBook = new Book(request.getBook().getIsbn(), request.getBook().getTitle()
                                ,request.getBook().getAuthor(), request.getBook().getPublisher());
        Integer timelineSeq = timelineService.createTimeline(request.getMemberSeq(), createBook, request.getContents());
        return new CreateTimelineResponse(timelineSeq);
    }

    //타임라인 삭제
    @PostMapping("/delete/{timelineSeq}")
    public void deleteTimeline(@PathVariable("timelineSeq") Integer timelineSeq){
        timelineService.deleteTimeline(timelineSeq);
    }

    //특정회원이 업로드 한 타임라인 조회
    @GetMapping("/upload/{memberSeq}")
    public Result timelinesByMember(@PathVariable("memberSeq") Integer memberSeq){
        List<Timeline> findTimelines =  timelineService.getMemberTimelines(memberSeq);
        List<TimelineDto> collect = findTimelines.stream()
                .map(t -> new TimelineDto(
                          t.getTimelineSeq()
                        , t.getMember().getMemberId()
                        , t.getMember().getNickname()
                        , t.getContents()
                        , t.getLikes()
                        , t.getBook().getTitle()
                        , t.getBook().getAuthor()
                        , t.getContents()
                        , t.getCreatedDate()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    //팔로잉한 회원들의 타임라인 조회
    @GetMapping("/following/{memberSeq}")
    public Result timelinesByFollowing(@PathVariable("memberSeq") Integer memberSeq){
        List<Timeline> followedTimelines = timelineService.getFollowedTimelines(memberSeq);
        List<TimelineDto> collect = followedTimelines.stream()
                .map(t -> new TimelineDto(
                          t.getTimelineSeq()
                        , t.getMember().getMemberId()
                        , t.getMember().getNickname()
                        , t.getContents()
                        , t.getLikes()
                        , t.getBook().getTitle()
                        , t.getBook().getAuthor()
                        , t.getContents()
                        , t.getCreatedDate()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    //타임라인 ID로 타임라인 단건 조회
    @GetMapping("/{timelineSeq}")
    public Result timelineByTimelineId(@PathVariable("timelineSeq")Integer timelineSeq){
        Timeline timeline = timelineService.getTimelineByTimeLineId(timelineSeq);
        TimelineDto timelineDto = new TimelineDto(
                                  timeline.getTimelineSeq()
                                , timeline.getMember().getMemberId()
                                , timeline.getMember().getNickname()
                                , timeline.getContents()
                                , timeline.getLikes()
                                , timeline.getBook().getTitle()
                                , timeline.getBook().getAuthor()
                                , timeline.getBook().getPublisher()
                                , timeline.getCreatedDate()
                                );
        return new Result(timelineDto);
    }
}
