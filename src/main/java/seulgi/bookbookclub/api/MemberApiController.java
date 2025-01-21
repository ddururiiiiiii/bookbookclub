package seulgi.bookbookclub.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.dto.*;
import seulgi.bookbookclub.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/members")
    public CreateMemberResponse saveMember(@RequestBody CreateMemberRequest request){
        Member member = new Member(request.getMemberId(), request.getName()
                                 , request.getNickName(), request.getInfo());
        Integer seq = memberService.join(member);
        return new CreateMemberResponse(seq);
    }

    //회원정보 수정
    @PostMapping("/members/{seq}")
    public UpdateMemberResponse updateMember(@PathVariable("seq") Integer seq
                                            , @RequestBody UpdateMemberRequest request){
        memberService.updateMember(seq, request.getPassword(), request.getNickName(), request.getInfo());
        Member findMember = memberService.findByMemberSeq(seq);
        return new UpdateMemberResponse(findMember.getMemberSeq(), findMember.getNickname(), findMember.getInfo());
    }

    @GetMapping("/members")
    public Result members(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getMemberId(), m.getNickname(), m.getNickname()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

}
