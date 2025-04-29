package ddururi.bookbookclub.domain.like.service;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import ddururi.bookbookclub.domain.like.entity.Like;
import ddururi.bookbookclub.domain.like.exception.LikeException;
import ddururi.bookbookclub.domain.like.repository.LikeRepository;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 좋아요(Like) 관련 비즈니스 로직 처리 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final FeedRepository feedRepository;

    /**
     * 피드 좋아요
     * @param user 좋아요 누른 사용자
     * @param feedId 좋아요 누를 피드 ID
     */
    @Transactional
    public void likeFeed(User user, Long feedId) {
        // 이미 좋아요를 눌렀는지 확인
        boolean alreadyLiked = likeRepository.existsByUserIdAndFeedId(user.getId(), feedId);
        if (alreadyLiked) {
            throw new LikeException(ErrorCode.ALREADY_LIKED);
        }

        // 피드 존재 여부 확인
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new LikeException(ErrorCode.FEED_NOT_FOUND));

        // 좋아요 저장
        Like like = Like.create(user, feed);
        likeRepository.save(like);
    }

    /**
     * 피드 좋아요 취소
     * @param user 좋아요 취소할 사용자
     * @param feedId 좋아요 취소할 피드 ID
     */
    @Transactional
    public void unlikeFeed(User user, Long feedId) {
        // 좋아요 존재 여부 확인
        Like like = likeRepository.findByUserIdAndFeedId(user.getId(), feedId)
                .orElseThrow(() -> new LikeException(ErrorCode.LIKE_NOT_FOUND));

        likeRepository.delete(like);
    }

    /**
     * 피드의 좋아요 수 조회
     * @param feedId 피드 ID
     * @return 좋아요 수
     */
    public long getLikeCount(Long feedId) {
        return likeRepository.countByFeedId(feedId);
    }
}
