package ddururi.bookbookclub.domain.report.service;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.exception.FeedNotFoundException;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import ddururi.bookbookclub.domain.report.entity.Report;
import ddururi.bookbookclub.domain.report.enums.ReasonType;
import ddururi.bookbookclub.domain.report.exception.AlreadyReportedException;
import ddururi.bookbookclub.domain.report.repository.ReportRepository;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.domain.user.exception.UserNotFoundException;
import ddururi.bookbookclub.domain.user.repository.UserRepository;
import ddururi.bookbookclub.global.config.ReportProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 피드 신고 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final ReportProperties reportProperties;

    /** 피드 신고 처리 */
    public void reportFeed(Long feedId, Long reporterId, ReasonType reason) {
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(UserNotFoundException::new);

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(FeedNotFoundException::new);

        if (reportRepository.existsByReporterAndFeed(reporter, feed)) {
            throw new AlreadyReportedException();
        }

        Report report = Report.of(reporter, feed, reason);
        reportRepository.save(report);

        // 누적 신고 수 확인
        long reportCount = reportRepository.countByFeed(feed);
        int threshold = reportProperties.getBlindThreshold();

        if (reportCount >= threshold && !feed.isBlinded()) {
            feed.blind();
            feedRepository.save(feed);
        }
    }
}
