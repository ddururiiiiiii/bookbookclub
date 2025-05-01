package ddururi.bookbookclub.domain.report.repository;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.report.entity.Report;
import ddururi.bookbookclub.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 신고 관련 JPA 레포지토리
 */
public interface ReportRepository extends JpaRepository<Report, Long> {

    /** 동일 사용자가 이미 해당 피드를 신고했는지 여부 확인 */
    boolean existsByReporterAndFeed(User reporter, Feed feed);

    /** 신고 당한 횟수 조회 */
    long countByFeed(Feed feed);

}
