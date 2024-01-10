package toyproject.bookbookclub.domain.Members;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = new MemberRepository();

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Member member = new Member("testid", "닉네임", "1234");

        //when
        Member savedMember = memberRepository.save(member);

        //then
        //assertion.assert
        //option + enter -> 단축
        Member findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll(){
        //given
        Member member1 = new Member("testid1", "닉네임1", "1234");
        Member member2 = new Member("testid2", "닉네임2", "5678");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> result = memberRepository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void updateMember(){

        //given
        Member member = new Member("testid1", "닉네임1", "1234");

        Member savedMember = memberRepository.save(member);
        String memberId = savedMember.getId();

        //when
        Member updateParam = new Member("testid2", "닉네임2", "5678");
        memberRepository.update(memberId, updateParam);

        Member findId = memberRepository.findById(memberId);

        //then
        assertThat(findId.getId()).isEqualTo(updateParam.getId());
        assertThat(findId.getNickName()).isEqualTo(updateParam.getNickName());
        assertThat(findId.getPassword()).isEqualTo(updateParam.getPassword());


    }

}