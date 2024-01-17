package toyproject.bookbookclub.web.member.basic;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.bookbookclub.domain.Members.Member;
import toyproject.bookbookclub.domain.Members.MemberRepository;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/basic/members")
public class BasicMemberController {

    /**
     * @RequiredArgsConstructor : final이 붙은 멤버변수만 생성자를 자동으로 만들어 준다.
     * 이렇게 생성자가 딱 1개만 있으면 스프링이 해당 생성자에 @Autowired 로 의존관계를 주입해준다. 따라서 final 키워드를 빼면 안된다!, 그러면 ItemRepository 의존관계 주입이 안된다.
     * 스프링 핵심원리 - 기본편 강의 참고
     *
     * @GetMapping
     * @PostConstruct
     */

    private final MemberRepository memberRepository;

    /**
     * 회원 전체 목록 조회
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
     * 회원 상세 조회
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

    @GetMapping("/join")
    public String joinForm(){
        return "basic/joinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute Member member){
        memberRepository.save(member);
        return "basic/member";
    }

    @GetMapping("/{memberId}/edit")
    public String editForm(@PathVariable String memberId, Model model){
        Member member = memberRepository.findById(memberId);
        model.addAttribute("member", member);
        return "basic/editForm";
    }

    @PostMapping("/{memberId}/edit")
    public String edit(@PathVariable String memberId, @ModelAttribute Member member){
        memberRepository.update(memberId, member);
        return "redirect:/basic/members/{memberId}";
    }

    /**
     * 테스트용 데이터
     * 테스트용 데이터가 없으면 회원 목록 기능이 정상 동작하는지 확인하기 어렵다. @PostConstruct : 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.
     * 여기서는 간단히 테스트용 테이터를 넣기 위해서 사용했다.
     */
    @PostConstruct
    public  void init(){
        memberRepository.save(new Member("testid1", "닉네임1", "1234"));
        memberRepository.save(new Member("testid2", "닉네임2", "5678"));

    }

}
