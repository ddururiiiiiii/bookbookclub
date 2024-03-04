package toyproject.bookbookclub.web.timeline;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.bookbookclub.domain.Timeline.TimeLineRepository;
import toyproject.bookbookclub.domain.Timeline.Timeline;

import java.time.LocalDateTime;
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
        return "/timeline/allTimeline";
    }

    @GetMapping("/{timelineId}")
    public String memberTimeline(@PathVariable String timelineId, Model model) {
        Timeline timeline = timeLineRepository.findByTimelineId(timelineId);
        model.addAttribute("timeline", timeline);
        return "timeline/memberTimeline";
    }

    @GetMapping("/add")
    public String addForm(){
        return "timeline/addForm";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Timeline timeline, RedirectAttributes redirectAttributes){
        Timeline savedTimeline = timeLineRepository.save(timeline);
        redirectAttributes.addAttribute("timelineId", savedTimeline.getTimelineId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/timeline/{timelineId}";
    }

    @GetMapping("/{timelineId}/timelineEdit")
    public String editForm(@PathVariable String timelineId, Model model){
        Timeline timeline = timeLineRepository.findByTimelineId(timelineId);
        model.addAttribute("timeline", timeline);
        return "timeline/editForm";
    }

    @PostMapping("/{timelineId}/timelineEdit")
    public String edit(@PathVariable String timelineId, @ModelAttribute Timeline timeline){
        timeLineRepository.update(timelineId, timeline);
        return "redirect:/timeline/{timelineId}";
    }

    @PostConstruct
    public void init(){
        timeLineRepository.save(new Timeline("1", "bookId1", "bookImg1", "testid1", "content1", LocalDateTime.now()));
        timeLineRepository.save(new Timeline("2", "bookId2", "bookImg2", "testid2", "content2", LocalDateTime.now()));
    }
}
