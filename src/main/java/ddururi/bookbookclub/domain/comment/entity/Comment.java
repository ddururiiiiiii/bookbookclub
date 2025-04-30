package ddururi.bookbookclub.domain.comment.entity;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 댓글(Comment) 엔티티
 * - 사용자가 특정 피드에 작성한 댓글을 저장
 * - 피드(Feed)와 댓글(Comment)은 다대일 관계
 * - 대댓글은 지원하지 않음
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 댓글 내용 */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /** 댓글 작성자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** 댓글이 작성된 피드 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    /** 댓글 좋아요 수 */
    @Column(nullable = false)
    private int likeCount = 0;

    /** 댓글 생성일 */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 댓글 수정일 */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * 댓글 생성 정적 메서드
     */
    public static Comment create(String content, User user, Feed feed) {
        Comment comment = new Comment();
        comment.content = content;
        comment.user = user;
        comment.feed = feed;
        return comment;
    }

    /**
     * 댓글 내용 수정
     */
    public void updateContent(String content) {
        this.content = content;
    }

}
