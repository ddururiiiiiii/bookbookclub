package ddururi.bookbookclub.domain.like.service;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import ddururi.bookbookclub.domain.like.entity.Like;
import ddururi.bookbookclub.domain.like.exception.LikeException;
import ddururi.bookbookclub.domain.like.repository.LikeRepository;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LikeServiceTest {

    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private FeedRepository feedRepository;

    private User user;
    private Feed feed;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.create("test@example.com", "encodedPassword", "nickname");
        feed = Feed.create(user, "test content");
    }

    @Nested
    @DisplayName("likeFeed 메서드 테스트")
    class LikeFeed {

        @Test
        @DisplayName("정상적으로 좋아요를 누를 수 있다")
        void likeFeed_success() {
            // given
            when(likeRepository.existsByUserIdAndFeedId(user.getId(), 1L)).thenReturn(false);
            when(feedRepository.findById(1L)).thenReturn(Optional.of(feed));

            // when
            likeService.likeFeed(user, 1L);

            // then
            verify(likeRepository, times(1)).save(any(Like.class));
        }

        @Test
        @DisplayName("이미 좋아요를 누른 경우 예외를 던진다")
        void likeFeed_alreadyLiked() {
            // given
            when(likeRepository.existsByUserIdAndFeedId(user.getId(), 1L)).thenReturn(true);

            // when & then
            assertThatThrownBy(() -> likeService.likeFeed(user, 1L))
                    .isInstanceOf(LikeException.class)
                    .hasMessage(ErrorCode.ALREADY_LIKED.getMessage());

            verify(feedRepository, never()).findById(anyLong());
            verify(likeRepository, never()).save(any(Like.class));
        }

        @Test
        @DisplayName("존재하지 않는 피드에 좋아요를 누르면 예외를 던진다")
        void likeFeed_feedNotFound() {
            // given
            when(likeRepository.existsByUserIdAndFeedId(user.getId(), 1L)).thenReturn(false);
            when(feedRepository.findById(1L)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> likeService.likeFeed(user, 1L))
                    .isInstanceOf(LikeException.class)
                    .hasMessage(ErrorCode.FEED_NOT_FOUND.getMessage());

            verify(likeRepository, never()).save(any(Like.class));
        }
    }

    @Nested
    @DisplayName("unlikeFeed 메서드 테스트")
    class UnlikeFeed {

        @Test
        @DisplayName("정상적으로 좋아요를 취소할 수 있다")
        void unlikeFeed_success() {
            // given
            Like like = Like.create(user, feed);
            when(likeRepository.findByUserIdAndFeedId(user.getId(), 1L)).thenReturn(Optional.of(like));

            // when
            likeService.unlikeFeed(user, 1L);

            // then
            verify(likeRepository, times(1)).delete(like);
        }

        @Test
        @DisplayName("좋아요 기록이 없으면 예외를 던진다")
        void unlikeFeed_likeNotFound() {
            // given
            when(likeRepository.findByUserIdAndFeedId(user.getId(), 1L)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> likeService.unlikeFeed(user, 1L))
                    .isInstanceOf(LikeException.class)
                    .hasMessage(ErrorCode.LIKE_NOT_FOUND.getMessage());

            verify(likeRepository, never()).delete(any(Like.class));
        }
    }

    @Nested
    @DisplayName("getLikeCount 메서드 테스트")
    class GetLikeCount {

        @Test
        @DisplayName("피드의 좋아요 수를 조회할 수 있다")
        void getLikeCount_success() {
            // given
            when(likeRepository.countByFeedId(1L)).thenReturn(5L);

            // when
            long count = likeService.getLikeCount(1L);

            // then
            assertThat(count).isEqualTo(5L);
        }
    }
}
