package ddururi.bookbookclub.domain.feed.entity;

import ddururi.bookbookclub.domain.book.entity.Book;
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
 * 피드(Feed) 엔티티
 * - 사용자가 작성한 게시글 정보를 저장
 * - 작성자(User)와 다대일 관계
 * - 생성일, 수정일 자동 관리
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 작성자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** 책 정보 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    /** 피드 내용 */
    @Column(nullable = false, length = 1000)
    private String content;

    /** 작성일 */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 수정일 */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 피드 생성 정적 메서드
     */
    public static Feed create(User user, Book book, String content) {
        Feed feed = new Feed();
        feed.user = user;
        feed.book = book;
        feed.content = content;
        return feed;
    }

    /**
     * 피드 내용 수정
     * @param newContent 수정할 내용
     */
    public void updateContent(String newContent) {
        this.content = newContent;
    }
}
