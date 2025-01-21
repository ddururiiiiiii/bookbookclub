package seulgi.bookbookclub.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입_성공() {
        // given
        Member member = new Member("testId", "1234", "닉네임11", "정보");

        // when
        Integer savedId = memberService.join(member);

        // then
        Member foundMember = memberRepository.findByMemberSeq(savedId);
        assertNotNull(foundMember);
        assertEquals("testId", foundMember.getMemberId());
        assertEquals("닉네임11", foundMember.getNickname());
    }


    @Test
    public void 중복회원_예외() throws Exception
    {
        //given
        Member member1 = new Member("testId", "1234", "닉네임1", "정보");
        Member member2 = new Member("testId", "5678", "닉네임2", "정보");

        //when
        memberService.join(member1);

        //then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
        assertEquals("이미 존재하는 회원입니다.", exception.getMessage());
    }
}