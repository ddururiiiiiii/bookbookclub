package toyproject.bookbookclub.web.timeline;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.bookbookclub.domain.FileStore;
import toyproject.bookbookclub.domain.Members.JoinForm;
import toyproject.bookbookclub.domain.Members.Member;
import toyproject.bookbookclub.domain.Members.MemberRepository;
import toyproject.bookbookclub.domain.Timeline.TimeLineRepository;
import toyproject.bookbookclub.domain.Timeline.Timeline;
import toyproject.bookbookclub.domain.UploadFile;
import toyproject.bookbookclub.web.validation.TimelineValidator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/timeline")
public class TimelineController {

    private final TimelineValidator timelineValidator;
    private final TimeLineRepository timeLineRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    @InitBinder("timeline")
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(timelineValidator);
    }

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
    public String addForm(HttpServletRequest request, Model model) throws IOException {
        String loginId = (String) request.getSession().getAttribute("loginId");

        Member member = memberRepository.findById(loginId);
        UploadFile profileImage = member.getProfileImage();
        Timeline timeline = new Timeline();
        timeline.setMemberId(loginId);

        model.addAttribute("timeline", timeline);
        model.addAttribute("member", member);
        model.addAttribute("profileImage", profileImage);
        return "timeline/addForm";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute Timeline timeline
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "timeline/addForm";
        }


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
//        timeLineRepository.save(new Timeline("1", "bookId1", "bookImg1", "testid1", "content1", LocalDateTime.now()));
//        timeLineRepository.save(new Timeline("2", "bookId2", "bookImg2", "testid2", "content2", LocalDateTime.now()));
    }
}
