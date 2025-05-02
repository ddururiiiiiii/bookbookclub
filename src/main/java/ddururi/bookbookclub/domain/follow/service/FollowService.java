package ddururi.bookbookclub.domain.follow.service;

import ddururi.bookbookclub.domain.follow.dto.FollowActionResponse;
import ddururi.bookbookclub.domain.follow.dto.FollowResponse;
import ddururi.bookbookclub.domain.follow.entity.Follow;
import ddururi.bookbookclub.domain.follow.exception.AlreadyFollowingException;
import ddururi.bookbookclub.domain.follow.exception.FollowNotFoundException;
import ddururi.bookbookclub.domain.follow.repository.FollowRepository;
import ddururi.bookbookclub.domain.user.dto.UserSummaryResponse;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.domain.user.exception.UserNotFoundException;
import ddururi.bookbookclub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    /**
     * 팔로우
     */
    public FollowActionResponse follow(Long followerId, Long followingId) {
        User follower = findUserById(followerId);
        User following = findUserById(followingId);

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new AlreadyFollowingException();
        }

        Follow follow = Follow.create(follower, following);
        followRepository.save(follow);

        long followerCount = followRepository.countByFollowing(following);

        return new FollowActionResponse(follow.getId(), "팔로우 완료", followerCount);
    }

    /**
     * 언팔로우
     */
    public FollowActionResponse unfollow(Long followerId, Long followingId) {
        User follower = findUserById(followerId);
        User following = findUserById(followingId);

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(FollowNotFoundException::new);

        followRepository.delete(follow);

        long followerCount = followRepository.countByFollowing(following);

        return new FollowActionResponse(follow.getId(), "언팔로우 완료", followerCount);
    }

    /**
     * 팔로워 목록
     */
    public List<FollowResponse> getFollowers(Long userId) {
        User user = findUserById(userId);
        return followRepository.findAllByFollowing(user).stream()
                .map(f -> new FollowResponse(f.getId(), UserSummaryResponse.from(f.getFollower())))
                .toList();
    }

    /**
     * 팔로잉 목록
     */
    public List<FollowResponse> getFollowings(Long userId) {
        User user = findUserById(userId);
        return followRepository.findAllByFollower(user).stream()
                .map(f -> new FollowResponse(f.getId(), UserSummaryResponse.from(f.getFollowing())))
                .toList();
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
