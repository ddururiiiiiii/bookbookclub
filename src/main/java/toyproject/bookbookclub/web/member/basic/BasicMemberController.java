package toyproject.bookbookclub.web.member.basic;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import toyproject.bookbookclub.domain.FileStore;
import toyproject.bookbookclub.domain.Members.Member;
import toyproject.bookbookclub.domain.Members.MemberJoinForm;
import toyproject.bookbookclub.domain.Members.MemberRepository;
import toyproject.bookbookclub.domain.UploadFile;
import toyproject.bookbookclub.web.validation.MemberValidator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/basic/members")
public class BasicMemberController {

    private final MemberValidator memberValidator;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;
//
    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(memberValidator);
    }

    /**
     * 회원 목록 조회
     * @param model
     * @return
     */
    @GetMapping
    public String members(Model model){
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "basic/members";
    }

    /**
     * 회원 단건 조회
     * @param memberId
     * @param model
     * @return
     */
    @GetMapping("/{memberId}")
    public String member(@PathVariable String memberId, Model model) {
        Member member = memberRepository.findById(memberId);
        model.addAttribute("member", member);
        return "basic/member";
    }

    /**
     * 회원 가입 폼
     * @param model
     * @return
     */
    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute("member", new Member());
        return "basic/joinForm";
    }

    /**
     * 회원 가입
     * @param form
     * @param bindingResult
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("member") MemberJoinForm form
    , BindingResult bindingResult
    , RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()){
            return "basic/joinForm";
        }
        UploadFile profileImage = fileStore.storeFile(form.getProfileImage());

        Member member = new Member();
        member.setId(form.getId());
        member.setPassword(form.getPassword());
        member.setNickName(form.getNickName());
        member.setProfileImage(profileImage);

        Member savedMember = memberRepository.save(member);
//        return "basic/member";
        redirectAttributes.addAttribute("memberId", savedMember.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/members/{memberId}";
    }

    /**
     * 회원 수정 폼
     * @param memberId
     * @param model
     * @return
     */
    @GetMapping("/{memberId}/edit")
    public String editForm(@PathVariable String memberId, Model model){
        Member member = memberRepository.findById(memberId);
        model.addAttribute("member", member);
        return "basic/editForm";
    }

    /**
     * 회원 정보 수정
     * @param memberId
     * @param member
     * @return
     */
    @PostMapping("/{memberId}/edit")
    public String edit(@PathVariable String memberId, @ModelAttribute Member member){
        memberRepository.update(memberId, member);
        return "redirect:/basic/members/{memberId}";
    }

    /**
     * 이미지 조회
     * @param filename
     * @return
     * @throws MalformedURLException
     */
    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/attach/{memberId}")
    public ResponseEntity<UrlResource> downloadAttach(@PathVariable String memberId) throws MalformedURLException {

        Member member = memberRepository.findById(memberId);
        String storeFileName = member.getProfileImage().getStoreFileName();
        String uploadFileName = member.getProfileImage().getUploadFileName();

        UrlResource resource =
                 new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" +
                encodedUploadFileName + "\"";
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }


    /**
     * 테스트용 데이터
     * 테스트용 데이터가 없으면 회원 목록 기능이 정상 동작하는지 확인하기 어렵다
     * . @PostConstruct : 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.
     * 여기서는 간단히 테스트용 테이터를 넣기 위해서 사용했다.
     */
    @PostConstruct
    public  void init(){
        memberRepository.save(new Member("testid1", "닉네임1", "1234"));
        memberRepository.save(new Member("testid2", "닉네임2", "5678"));
    }
}
