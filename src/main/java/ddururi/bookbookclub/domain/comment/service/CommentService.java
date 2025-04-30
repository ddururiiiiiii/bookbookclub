package ddururi.bookbookclub.domain.comment.service;

import ddururi.bookbookclub.domain.comment.entity.Comment;
import ddururi.bookbookclub.domain.comment.exception.CommentAccessDeniedException;
import ddururi.bookbookclub.domain.comment.exception.CommentNotFoundException;
import ddururi.bookbookclub.domain.comment.repository.CommentRepository;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import ddururi.bookbookclub.domain.feed.exception.FeedNotFoundException;
import ddururi.bookbookclub.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 댓글(Comment) 서비스
 * - 댓글 등록, 조회, 삭제 기능 제공
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final FeedRepository feedRepository;

    /**
     * 댓글 등록
     * @param content 댓글 내용
     * @param user 작성자
     * @param feedId 댓글이 달릴 피드 ID
     * @return 저장된 댓글
     */
    public Comment createComment(String content, User user, Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(FeedNotFoundException::new);

        Comment comment = Comment.create(content, user, feed);
        return commentRepository.save(comment);
    }

    /**
     * 특정 피드에 작성된 댓글 목록 조회 (최신순)
     * @param feedId 피드 ID
     * @return 댓글 리스트
     */
    public List<Comment> getCommentsByFeed(Long feedId) {
        return commentRepository.findByFeedIdOrderByCreatedAtDesc(feedId);
    }

    /**
     * 댓글 삭제
     * @param commentId 댓글 ID
     * @param user 삭제 요청 사용자
     */
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentAccessDeniedException();
        }

        commentRepository.delete(comment);
    }
}
