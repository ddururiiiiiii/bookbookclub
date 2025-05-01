package ddururi.bookbookclub.domain.report.entity;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.report.enums.ReasonType;
import ddururi.bookbookclub.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 신고(Report) 엔티티
 * - 사용자가 특정 피드를 신고한 기록을 저장
 * - 사용자(User)와 피드(Feed)와 다대일 관계
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 신고한 사용자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User reporter;

    /** 신고된 피드 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    /** 신고 사유 (enum) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReasonType reason;

    /** 신고 시간 */
    @Column(nullable = false)
    private LocalDateTime reportedAt;

    private Report(User reporter, Feed feed, ReasonType reason) {
        this.reporter = reporter;
        this.feed = feed;
        this.reason = reason;
        this.reportedAt = LocalDateTime.now();
    }

    /** 신고 생성 정적 메서드 */
    public static Report of(User reporter, Feed feed, ReasonType reason) {
        return new Report(reporter, feed, reason);
    }
}
