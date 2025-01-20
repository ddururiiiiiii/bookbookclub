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
        Member member = new Member("testId", "1234", "닉네임1");

        // when
        String savedId = memberService.join(member);

        // then
        Member foundMember = memberRepository.findByMemberId(savedId).get(0);
        assertNotNull(foundMember);
        assertEquals("testId", foundMember.getMemberId());
        assertEquals("닉네임1", foundMember.getNickname());
    }


    @Test
    public void 중복회원_예외() throws Exception
    {
        //given
        Member member1 = new Member("testId", "1234", "닉네임1");
        Member member2 = new Member("testId", "5678", "닉네임2");

        //when
        memberService.join(member1);

        //then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
        assertEquals("이미 존재하는 회원입니다.", exception.getMessage());
    }
}