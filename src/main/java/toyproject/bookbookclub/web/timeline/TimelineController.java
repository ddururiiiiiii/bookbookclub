package toyproject.bookbookclub.web.timeline;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import toyproject.bookbookclub.domain.Members.Member;
import toyproject.bookbookclub.domain.Timeline.TimeLineRepository;
import toyproject.bookbookclub.domain.Timeline.Timeline;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timeline")
public class TimelineController {

    private final TimeLineRepository timeLineRepository;

    @GetMapping
    public String allTimeline(Model model){
        List<Timeline> timelines = timeLineRepository.findAll();
        model.addAttribute("timelines", timelines);
        return "timeline/allTimeline";
    }

    @GetMapping("/{timelineId}")
    public String memberTimeline(@PathVariable String timelineId, Model model) {
        Timeline timeline = timeLineRepository.findByTimelineId(timelineId);
        model.addAttribute("timeline", timeline);
        return "timeline/memberTimeline";
    }

    @PostConstruct
    public void init(){
        timeLineRepository.save(new Timeline("1", "bookId1", "bookImg1", "testid1", "content1", "2024-01-15"));
        timeLineRepository.save(new Timeline("2", "bookId2", "bookImg2", "testid2", "content2", "2024-01-16"));
    }
}
