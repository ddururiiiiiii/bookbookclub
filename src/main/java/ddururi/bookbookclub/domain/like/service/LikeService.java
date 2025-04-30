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
     * 좋아요 토글 기능
     * - 이미 좋아요 되어 있으면 삭제 (취소)
     * - 좋아요 안 되어 있으면 생성
     *
     * @param user 사용자
     * @param feedId 피드 ID
     * @return 토글 후 상태 (true: 좋아요됨, false: 좋아요 취소됨)
     */
    @Transactional
    public boolean toggleLike(User user, Long feedId) {
        // 피드 존재 여부 확인
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new LikeException(ErrorCode.FEED_NOT_FOUND));

        // 좋아요가 이미 존재하면 삭제, 없으면 생성
        return likeRepository.findByUserIdAndFeedId(user.getId(), feedId)
                .map(existingLike -> {
                    likeRepository.delete(existingLike);
                    return false;
                })
                .orElseGet(() -> {
                    likeRepository.save(Like.create(user, feed));
                    return true;
                });
    }

    /**
     * 피드 좋아요 (단건)
     * - 중복 방지 확인 포함
     */
    @Transactional
    public void likeFeed(User user, Long feedId) {
        if (likeRepository.existsByUserIdAndFeedId(user.getId(), feedId)) {
            throw new LikeException(ErrorCode.ALREADY_LIKED);
        }

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new LikeException(ErrorCode.FEED_NOT_FOUND));

        likeRepository.save(Like.create(user, feed));
    }

    /**
     * 피드 좋아요 취소 (단건)
     */
    @Transactional
    public void unlikeFeed(User user, Long feedId) {
        Like like = likeRepository.findByUserIdAndFeedId(user.getId(), feedId)
                .orElseThrow(() -> new LikeException(ErrorCode.LIKE_NOT_FOUND));

        likeRepository.delete(like);
    }

    /**
     * 피드의 좋아요 수 조회
     */
    public long getLikeCount(Long feedId) {
        return likeRepository.countByFeedId(feedId);
    }

    /**
     * 사용자가 특정 피드에 좋아요를 눌렀는지 여부 조회
     */
    public boolean hasUserLiked(Long userId, Long feedId) {
        return likeRepository.existsByUserIdAndFeedId(userId, feedId);
    }
}
