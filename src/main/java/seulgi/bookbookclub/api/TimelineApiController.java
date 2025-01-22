package seulgi.bookbookclub.api;


import lombok.RequiredArgsConstructor;
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

    //타임라인 조회
    @GetMapping("/{seq}")
    public Result timelines(@PathVariable("seq") Integer MemberSeq){
        List<Timeline> findTimelines =  timelineService.getMemberTimelines(MemberSeq);
        List<TimelineDto> collect = findTimelines.stream()
                .map(t -> new TimelineDto(
                        t.getTimelineSeq(),
                        t.getMember().getMemberId(),
                        t.getMember().getNickname(),
                        t.getLikes(),
                        t.getBook().getTitle(),
                        t.getBook().getAuthor(),
                        t.getContents(),
                        t.getCreatedDate()))
                .collect(Collectors.toList());
        return new Result(collect);
    }



}
