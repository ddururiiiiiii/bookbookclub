package seulgi.bookbookclub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timelineSeq;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "member_seq", nullable = false)
    private Member member;

    @Column(nullable = false, length = 600)
    private String contents;

    @Column(nullable = false)
    private int likes = 0;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "book_seq", nullable = false)
    private Book book; // BOOK 테이블 참조 (FK)

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private State state = State.ACTIVE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    public Timeline(Member member, Book book, String contents ) {
        this.member = member;
        this.book = book;
        this.contents = contents;
    }

    // 생성 메서드
    public static Timeline createTimeline(Member member, Book book, String contents) {
        if (member == null) throw new IllegalArgumentException("Member는 null일 수 없습니다.");
        if (book == null) throw new IllegalArgumentException("Book은 null일 수 없습니다.");
        if (contents == null || contents.isBlank()) throw new IllegalArgumentException("Contents는 필수입니다.");
        return new Timeline(member, book, contents);
    }

    //논리 삭제
    public void delete() {
        this.state = State.INACTIVE;
    }
}
