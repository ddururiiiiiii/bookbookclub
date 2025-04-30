package ddururi.bookbookclub.domain.comment.repository;

import ddururi.bookbookclub.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 댓글(Comment) 레포지토리
 * - 댓글 CRUD 및 피드 ID 기준 댓글 조회 기능 제공
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 피드 ID에 작성된 댓글 목록 조회 (최신순 정렬)
     * @param feedId 피드 ID
     * @return 댓글 리스트
     */
    List<Comment> findByFeedIdOrderByCreatedAtDesc(Long feedId);
}
