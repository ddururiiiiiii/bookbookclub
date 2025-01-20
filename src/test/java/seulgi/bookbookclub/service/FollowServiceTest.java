package seulgi.bookbookclub.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seulgi.bookbookclub.domain.Follow;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.repository.FollowRepository;
import seulgi.bookbookclub.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FollowServiceTest {
    
    @Autowired FollowService followService;
    @Autowired MemberRepository memberRepository;
    @Autowired FollowRepository followRepository;

    @Test
    void 팔로우추가() {
        // given
        Member follower = new Member("followerId", "1234", "팔로워");
        Member following = new Member("followingId", "5678", "팔로잉");

        memberRepository.save(follower);
        memberRepository.save(following);

        // when
        followService.follow(follower, following);

        // then
        List<Follow> follows = followRepository.findFollowingsByMember(follower.getMemberSeq());
        assertEquals(1, follows.size());
        assertEquals(following.getMemberSeq(), follows.get(0).getFollowing().getMemberSeq());
    }

    @Test
    void 팔로우중복방지() {
        // given
        Member follower = new Member("followerId", "1234", "팔로워");
        Member following = new Member("followingId", "5678", "팔로잉");

        memberRepository.save(follower);
        memberRepository.save(following);
        followService.follow(follower, following);

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            followService.follow(follower, following);
        });
        assertEquals("이미 팔로우 중입니다.", exception.getMessage());
    }

    @Test
    void 언팔로우() {
        // given
        Member follower = new Member("followerId", "1234", "팔로워");
        Member following = new Member("followingId", "5678", "팔로잉");

        memberRepository.save(follower);
        memberRepository.save(following);
        followService.follow(follower, following);

        // when
        followService.unfollow(follower, following);

        // then
        List<Follow> follows = followRepository.findFollowingsByMember(follower.getMemberSeq());
        assertTrue(follows.isEmpty());
    }

    @Test
    void 팔로잉을한_목록조회() {
        // given
        Member follower = new Member("followerId", "1234", "팔로워");
        Member following1 = new Member("following1Id", "5678", "팔로잉1");
        Member following2 = new Member("following2Id", "91011", "팔로잉2");

        memberRepository.save(follower);
        memberRepository.save(following1);
        memberRepository.save(following2);

        followService.follow(follower, following1);
        followService.follow(follower, following2);

        // when
        //follower를 팔로잉한 회원 목록 조회
        List<Follow> follows = followService.getFollowings(follower.getMemberSeq());

        // then
        assertEquals(2, follows.size());
        assertEquals(following1.getMemberSeq(), follows.get(0).getFollowing().getMemberSeq());
        assertEquals(following2.getMemberSeq(), follows.get(1).getFollowing().getMemberSeq());
    }

    @Test
    void 팔로잉을당한_목록조회() {
        // given
        Member following = new Member("followingId", "5678", "팔로잉");
        Member follower1 = new Member("follower1Id", "1234", "팔로워1");
        Member follower2 = new Member("follower2Id", "91011", "팔로워2");

        memberRepository.save(following);
        memberRepository.save(follower1);
        memberRepository.save(follower2);

        followService.follow(follower1, following);
        followService.follow(follower2, following);

        // when
        //following가 팔로잉한
        List<Follow> followers = followService.getFollowers(following.getMemberSeq());

        // then
        assertEquals(2, followers.size());
        assertEquals(follower1.getMemberSeq(), followers.get(0).getFollower().getMemberSeq());
        assertEquals(follower2.getMemberSeq(), followers.get(1).getFollower().getMemberSeq());
    }

}