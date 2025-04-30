package ddururi.bookbookclub.domain.comment.service;

import ddururi.bookbookclub.domain.comment.entity.Comment;
import ddururi.bookbookclub.domain.comment.exception.CommentAccessDeniedException;
import ddururi.bookbookclub.domain.comment.exception.CommentNotFoundException;
import ddururi.bookbookclub.domain.comment.repository.CommentRepository;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.exception.FeedNotFoundException;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import ddururi.bookbookclub.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private FeedRepository feedRepository;

    @InjectMocks
    private CommentService commentService;

    public CommentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("댓글 등록 테스트")
    class CreateCommentTest {

        @Test
        @DisplayName("댓글 등록 성공")
        void createComment_success() {
            // given
            User user = User.create("test@email.com", "encodedPassword", "nickname");
            Feed feed = mock(Feed.class);
            when(feedRepository.findById(1L)).thenReturn(Optional.of(feed));
            when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // when
            Comment comment = commentService.createComment("테스트 댓글", user, 1L);

            // then
            assertThat(comment.getContent()).isEqualTo("테스트 댓글");
            assertThat(comment.getUser()).isEqualTo(user);
            assertThat(comment.getFeed()).isEqualTo(feed);
        }

        @Test
        @DisplayName("댓글 등록 실패 - 피드 없음")
        void createComment_feedNotFound() {
            // given
            User user = User.create("test@email.com", "encodedPassword", "nickname");
            when(feedRepository.findById(1L)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> commentService.createComment("테스트 댓글", user, 1L))
                    .isInstanceOf(FeedNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("댓글 조회 테스트")
    class GetCommentsByFeedTest {

        @Test
        @DisplayName("피드 ID로 댓글 조회 성공")
        void getCommentsByFeed_success() {
            // given
            Feed feed = mock(Feed.class);
            List<Comment> comments = List.of(
                    Comment.create("댓글1", User.create("user1@email.com", "pass", "nickname1"), feed),
                    Comment.create("댓글2", User.create("user2@email.com", "pass", "nickname2"), feed)
            );
            when(commentRepository.findByFeedIdOrderByCreatedAtDesc(1L)).thenReturn(comments);

            // when
            List<Comment> result = commentService.getCommentsByFeed(1L);

            // then
            assertThat(result).hasSize(2);
            assertThat(result.get(0).getContent()).isEqualTo("댓글1");
            assertThat(result.get(1).getContent()).isEqualTo("댓글2");
        }
    }

    @Nested
    @DisplayName("댓글 삭제 테스트")
    class DeleteCommentTest {

        @Test
        @DisplayName("댓글 삭제 성공")
        void deleteComment_success() {
            // given
            User user = User.create("test@email.com", "encodedPassword", "nickname");
            ReflectionTestUtils.setField(user, "id", 1L);

            Feed feed = mock(Feed.class);
            Comment comment = Comment.create("삭제할 댓글", user, feed);
            ReflectionTestUtils.setField(comment, "id", 1L);

            when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

            // when
            commentService.deleteComment(1L, user);

            // then
            verify(commentRepository, times(1)).delete(comment);
        }


        @Test
        @DisplayName("댓글 삭제 실패 - 댓글 없음")
        void deleteComment_commentNotFound() {
            // given
            when(commentRepository.findById(1L)).thenReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> commentService.deleteComment(1L,
                    User.create("test@email.com", "encodedPassword", "nickname")))
                    .isInstanceOf(CommentNotFoundException.class);
        }

        @Test
        @DisplayName("댓글 삭제 실패 - 권한 없음")
        void deleteComment_accessDenied() {
            // given
            User owner = User.create("owner@email.com", "encodedPassword", "ownerNick");
            User otherUser = User.create("other@email.com", "encodedPassword", "otherNick");
            ReflectionTestUtils.setField(owner, "id", 1L);
            ReflectionTestUtils.setField(otherUser, "id", 2L);

            Feed feed = mock(Feed.class);
            Comment comment = Comment.create("권한 없는 삭제 시도", owner, feed);

            when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

            // when & then
            assertThatThrownBy(() -> commentService.deleteComment(1L, otherUser))
                    .isInstanceOf(CommentAccessDeniedException.class);
        }

    }
}
