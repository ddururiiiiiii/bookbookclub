package ddururi.bookbookclub.domain.like.service;

import ddururi.bookbookclub.domain.book.entity.Book;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private FeedRepository feedRepository;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User createUser(Long id) {
        User user = User.create("test@example.com", "encrypted", "nickname");
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

    private Feed createFeed(Long id) {
        // 테스트용 유저와 책은 임시 더미 객체
        User dummyUser = User.create("dummy@email.com", "pw", "닉네임");
        Book dummyBook = mock(Book.class); // 실제 동작 필요 없음
        Feed feed = Feed.create(dummyUser, dummyBook, "내용");

        ReflectionTestUtils.setField(feed, "id", id);
        return feed;
    }

    @Nested
    @DisplayName("likeFeed() 테스트")
    class LikeFeedTest {

        @Test
        @DisplayName("정상적으로 좋아요를 누를 수 있다")
        void likeFeed_success() {
            // given
            User user = createUser(1L);
            Feed feed = createFeed(1L);

            given(likeRepository.existsByUserIdAndFeedId(user.getId(), feed.getId())).willReturn(false);
            given(feedRepository.findById(feed.getId())).willReturn(Optional.of(feed));
            given(likeRepository.save(any(Like.class))).willReturn(Like.create(user, feed));

            // when & then
            assertDoesNotThrow(() -> likeService.likeFeed(user, feed.getId()));
        }

        @Test
        @DisplayName("이미 좋아요를 누른 경우 예외 발생")
        void likeFeed_alreadyLiked() {
            User user = createUser(1L);
            Long feedId = 1L;

            given(likeRepository.existsByUserIdAndFeedId(user.getId(), feedId)).willReturn(true);

            assertThatThrownBy(() -> likeService.likeFeed(user, feedId))
                    .isInstanceOf(LikeException.class)
                    .hasMessage(ErrorCode.ALREADY_LIKED.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 피드에 좋아요 시 예외 발생")
        void likeFeed_feedNotFound() {
            User user = createUser(1L);
            Long feedId = 1L;

            given(likeRepository.existsByUserIdAndFeedId(user.getId(), feedId)).willReturn(false);
            given(feedRepository.findById(feedId)).willReturn(Optional.empty());

            assertThatThrownBy(() -> likeService.likeFeed(user, feedId))
                    .isInstanceOf(LikeException.class)
                    .hasMessage(ErrorCode.FEED_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("unlikeFeed() 테스트")
    class UnlikeFeedTest {

        @Test
        @DisplayName("정상적으로 좋아요 취소 가능")
        void unlikeFeed_success() {
            User user = createUser(1L);
            Feed feed = createFeed(1L);
            Like like = Like.create(user, feed);

            given(likeRepository.findByUserIdAndFeedId(user.getId(), feed.getId()))
                    .willReturn(Optional.of(like));

            assertDoesNotThrow(() -> likeService.unlikeFeed(user, feed.getId()));
        }

        @Test
        @DisplayName("좋아요 기록 없을 경우 예외 발생")
        void unlikeFeed_notFound() {
            User user = createUser(1L);
            Long feedId = 1L;

            given(likeRepository.findByUserIdAndFeedId(user.getId(), feedId))
                    .willReturn(Optional.empty());

            assertThatThrownBy(() -> likeService.unlikeFeed(user, feedId))
                    .isInstanceOf(LikeException.class)
                    .hasMessage(ErrorCode.LIKE_NOT_FOUND.getMessage());
        }
    }

    @Nested
    @DisplayName("getLikeCount() 테스트")
    class GetLikeCountTest {

        @Test
        @DisplayName("피드의 좋아요 수 정상 조회")
        void getLikeCount_success() {
            Long feedId = 1L;
            given(likeRepository.countByFeedId(feedId)).willReturn(10L);

            long result = likeService.getLikeCount(feedId);

            assertThat(result).isEqualTo(10L);
        }
    }

    @Nested
    @DisplayName("hasUserLiked() 테스트")
    class HasUserLikedTest {

        @Test
        @DisplayName("사용자가 좋아요 눌렀는지 여부 조회")
        void hasUserLiked_success() {
            Long userId = 1L;
            Long feedId = 1L;

            given(likeRepository.existsByUserIdAndFeedId(userId, feedId)).willReturn(true);

            boolean result = likeService.hasUserLiked(userId, feedId);

            assertThat(result).isTrue();
        }
    }
}
