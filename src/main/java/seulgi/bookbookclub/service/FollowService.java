package seulgi.bookbookclub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seulgi.bookbookclub.domain.Follow;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.repository.FollowRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    //팔로우 추가
    @Transactional
    public void follow(Member follower, Member following) {
        //이미 팔로우 중인지 확인
        Follow existingFollow = followRepository.findFollow(follower.getMemberSeq(), following.getMemberSeq());
        if (existingFollow != null) {
            throw new IllegalStateException("이미 팔로우 중입니다.");
        }
        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
    }

    //언팔로우
    @Transactional
    public void unfollow(Member follower, Member following){
        //이미 팔로우 중인지 확인
        Follow existingFollow = followRepository.findFollow(follower.getMemberSeq(), following.getMemberSeq());
        if (existingFollow == null){
            throw new IllegalStateException("팔로우 관계가 존재하지 않습니다.");
        }
        followRepository.delete(existingFollow);
    }

    // memberSeqs를 팔로잉한 회원 목록 조회
    public List<Follow> getFollowers(Integer memberSeq) {
        return followRepository.findFollowerByMember(memberSeq);
    }

    // memberSeqs가 팔로잉한 회원 목록 조회
    public List<Follow> getFollowings(Integer memberSeq) {
        return followRepository.findFollowingsByMember(memberSeq);
    }
}
