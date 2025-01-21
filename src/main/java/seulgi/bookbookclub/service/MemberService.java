package seulgi.bookbookclub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    //회원가입
    @Transactional
    public Integer join(Member member){
        validateDuplicatieMember(member);
        memberRepository.save(member);
        return member.getMemberSeq();
    }

    //중복 아이디 체크
    private void validateDuplicatieMember(Member member) {
        List<Member> findMembers = memberRepository.findByMemberId(member.getMemberId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void updateMember(Integer memberSeq, String password, String nickName, String info) {
        // 기존 회원 조회
        Member existingMember = memberRepository.findByMemberSeq(memberSeq);
        if (existingMember == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        Member updatedMember = new Member(existingMember,
                password != null && !password.isBlank() ? password : existingMember.getPassword(),
                nickName != null && !nickName.isBlank() ? nickName : existingMember.getNickname(),
                info != null && !info.isBlank() ? info : existingMember.getInfo()
        );
        memberRepository.update(updatedMember);
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.finaAll();
    }

    //회원 조회
    public List<Member> findByMemberId(String memberId){
        return memberRepository.findByMemberId(memberId);
    }

    public Member findByMemberSeq(Integer memberId) {
        return memberRepository.findByMemberSeq(memberId);
    }
}
