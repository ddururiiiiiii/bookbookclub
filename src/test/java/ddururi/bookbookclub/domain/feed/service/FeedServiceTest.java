package ddururi.bookbookclub.domain.feed.service;

import ddururi.bookbookclub.domain.book.entity.Book;
import ddururi.bookbookclub.domain.book.repository.BookRepository;
import ddururi.bookbookclub.domain.feed.dto.FeedRequest;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import ddururi.bookbookclub.domain.like.service.LikeService;
import ddururi.bookbookclub.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * FeedService 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @Mock
    private FeedRepository feedRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LikeService likeService;

    @InjectMocks
    private FeedService feedService;

    @Test
    @DisplayName("피드 작성 성공 - ISBN으로 책 찾기")
    void createFeed_success_withIsbn() {
        // given
        User user = createUser();
        FeedRequest request = createFeedRequest("Effective Java", "Joshua Bloch", "Addison-Wesley", "9780134685991", "http://image.url");

        Book book = Book.create(request.getTitle(), request.getAuthor(), request.getPublisher(), request.getIsbn(), request.getThumbnailUrl());
        when(bookRepository.findByIsbn(request.getIsbn())).thenReturn(java.util.Optional.of(book));
        when(feedRepository.save(any(Feed.class))).thenAnswer(invocation -> {
            Feed feed = invocation.getArgument(0);
            setField(feed, "id", 1L); // 저장된 피드에 id 강제 설정
            return feed;
        });

        // when
        Feed feed = feedService.createFeed(user, request);

        // then
        assertThat(feed).isNotNull();
        assertThat(feed.getContent()).isEqualTo(request.getContent());
        assertThat(feed.getBook().getTitle()).isEqualTo(request.getTitle());
        assertThat(feed.getUser()).isEqualTo(user);

        verify(bookRepository, times(1)).findByIsbn(request.getIsbn());
        verify(feedRepository, times(1)).save(any(Feed.class));
    }

    @Test
    @DisplayName("피드 작성 성공 - ISBN 없고 Title+Author로 책 찾기")
    void createFeed_success_withTitleAndAuthor() {
        // given
        User user = createUser();
        FeedRequest request = createFeedRequest("Clean Code", "Robert C. Martin", "Prentice Hall", null, "http://image.url");

        Book book = Book.create(request.getTitle(), request.getAuthor(), request.getPublisher(), request.getIsbn(), request.getThumbnailUrl());
        when(bookRepository.findByTitleAndAuthor(request.getTitle(), request.getAuthor())).thenReturn(java.util.Optional.of(book));
        when(feedRepository.save(any(Feed.class))).thenAnswer(invocation -> {
            Feed feed = invocation.getArgument(0);
            setField(feed, "id", 2L);
            return feed;
        });

        // when
        Feed feed = feedService.createFeed(user, request);

        // then
        assertThat(feed).isNotNull();
        assertThat(feed.getContent()).isEqualTo(request.getContent());
        assertThat(feed.getBook().getAuthor()).isEqualTo(request.getAuthor());
        assertThat(feed.getUser()).isEqualTo(user);

        verify(bookRepository, times(1)).findByTitleAndAuthor(request.getTitle(), request.getAuthor());
        verify(feedRepository, times(1)).save(any(Feed.class));
    }

    private User createUser() {
        return User.create(
                "test@example.com",
                "encrypted-password",
                "testUser"
        );
    }

    private FeedRequest createFeedRequest(String title, String author, String publisher, String isbn, String thumbnailUrl) {
        FeedRequest request = new FeedRequest();
        setField(request, "title", title);
        setField(request, "author", author);
        setField(request, "publisher", publisher);
        setField(request, "isbn", isbn);
        setField(request, "thumbnailUrl", thumbnailUrl);
        setField(request, "content", "이 책 정말 좋아요!");
        return request;
    }

    // 테스트용 private util (setter 없는 필드 강제 설정)
    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
