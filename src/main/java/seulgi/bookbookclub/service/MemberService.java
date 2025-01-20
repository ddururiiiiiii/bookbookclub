package seulgi.bookbookclub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    //회원가입
    @Transactional
    public String join(Member member){
        validateDuplicatieMember(member);
        memberRepository.save(member);
        return member.getMemberId();
    }

    //중복 아이디 체크
    private void validateDuplicatieMember(Member member) {
        List<Member> findMembers = memberRepository.findByMemberId(member.getMemberId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.finaAll();
    }

    //회원 조회
    public List<Member> findByMemberId(String memberId){
        return memberRepository.findByMemberId(memberId);
    }
}
