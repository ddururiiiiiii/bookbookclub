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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    private ReportService reportService;
    private ReportRepository reportRepository;
    private FeedRepository feedRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        reportRepository = mock(ReportRepository.class);
        feedRepository = mock(FeedRepository.class);
        userRepository = mock(UserRepository.class);
        reportService = new ReportService(reportRepository, feedRepository, userRepository);
    }

    @Test
    void reportFeed_정상_신고_저장() {
        // given
        Long reporterId = 1L;
        Long feedId = 10L;
        ReasonType reason = ReasonType.SPAM;

        User user = mock(User.class);
        Feed feed = mock(Feed.class);

        when(userRepository.findById(reporterId)).thenReturn(Optional.of(user));
        when(feedRepository.findById(feedId)).thenReturn(Optional.of(feed));
        when(reportRepository.existsByReporterAndFeed(user, feed)).thenReturn(false);

        // when
        reportService.reportFeed(feedId, reporterId, reason);

        // then
        ArgumentCaptor<Report> reportCaptor = ArgumentCaptor.forClass(Report.class);
        verify(reportRepository, times(1)).save(reportCaptor.capture());
        Report savedReport = reportCaptor.getValue();

        assertThat(savedReport.getReporter()).isEqualTo(user);
        assertThat(savedReport.getFeed()).isEqualTo(feed);
        assertThat(savedReport.getReason()).isEqualTo(reason);
    }

    @Test
    void reportFeed_사용자_없음_예외() {
        // given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThrows(UserNotFoundException.class,
                () -> reportService.reportFeed(1L, 1L, ReasonType.SPAM));
    }

    @Test
    void reportFeed_피드_없음_예외() {
        // given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mock(User.class)));
        when(feedRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThrows(FeedNotFoundException.class,
                () -> reportService.reportFeed(1L, 1L, ReasonType.SPAM));
    }

    @Test
    void reportFeed_중복_신고_예외() {
        // given
        User user = mock(User.class);
        Feed feed = mock(Feed.class);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(feedRepository.findById(anyLong())).thenReturn(Optional.of(feed));
        when(reportRepository.existsByReporterAndFeed(user, feed)).thenReturn(true);

        // when & then
        assertThrows(AlreadyReportedException.class,
                () -> reportService.reportFeed(1L, 1L, ReasonType.SPAM));
    }
}
