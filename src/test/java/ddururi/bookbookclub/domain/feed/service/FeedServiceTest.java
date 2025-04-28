package ddururi.bookbookclub.domain.feed.service;

import ddururi.bookbookclub.domain.feed.dto.FeedResponse;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.global.exception.FeedNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedServiceTest {

    @Mock
    private FeedRepository feedRepository;

    @InjectMocks
    private FeedService feedService;

    private User user;
    private Feed feed;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.create(
                "test@example.com",
                "encoded-password",
                "테스트유저"
        );

        feed = Feed.create(user, "테스트 내용"); // ★ 여기 수정됨
    }

    @Test
    @DisplayName("피드 작성 성공")
    void createFeed_success() {
        when(feedRepository.save(any(Feed.class))).thenReturn(feed);

        Feed result = feedService.createFeed(user, "테스트 내용");

        assertThat(result.getContent()).isEqualTo("테스트 내용");
        verify(feedRepository, times(1)).save(any(Feed.class));
    }

    @Test
    @DisplayName("피드 수정 성공")
    void updateFeed_success() {
        when(feedRepository.findById(anyLong())).thenReturn(Optional.of(feed));

        Feed result = feedService.updateFeed(1L, "수정된 내용");

        assertThat(result.getContent()).isEqualTo("수정된 내용");
        verify(feedRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("피드 수정 실패 - 존재하지 않는 피드")
    void updateFeed_fail_notFound() {
        when(feedRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> feedService.updateFeed(1L, "수정된 내용"))
                .isInstanceOf(FeedNotFoundException.class);
    }

    @Test
    @DisplayName("피드 삭제 성공")
    void deleteFeed_success() {
        when(feedRepository.findById(anyLong())).thenReturn(Optional.of(feed));

        feedService.deleteFeed(1L);

        verify(feedRepository, times(1)).delete(feed);
    }

    @Test
    @DisplayName("피드 삭제 실패 - 존재하지 않는 피드")
    void deleteFeed_fail_notFound() {
        when(feedRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> feedService.deleteFeed(1L))
                .isInstanceOf(FeedNotFoundException.class);
    }

    @Test
    @DisplayName("피드 단건 조회 성공")
    void getFeed_success() {
        when(feedRepository.findById(anyLong())).thenReturn(Optional.of(feed));

        Feed result = feedService.getFeed(1L);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("테스트 내용");
    }

    @Test
    @DisplayName("피드 목록 조회 성공")
    void getFeeds_success() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Feed> page = new PageImpl<>(Collections.singletonList(feed));
        when(feedRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<FeedResponse> result = feedService.getFeeds(pageRequest);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getContent()).isEqualTo("테스트 내용");
    }
}
