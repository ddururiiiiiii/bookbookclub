package ddururi.bookbookclub.domain.book.service;

import ddururi.bookbookclub.domain.book.dto.BookRequest;
import ddururi.bookbookclub.domain.book.dto.BookResponse;
import ddururi.bookbookclub.domain.book.entity.Book;
import ddururi.bookbookclub.domain.book.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * BookService 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("책 등록 성공")
    void createBook_success() {
        // given
        BookRequest request = new BookRequest();
        setField(request, "title", "Clean Code");
        setField(request, "author", "Robert C. Martin");
        setField(request, "publisher", "Prentice Hall");
        setField(request, "isbn", "9780132350884");
        setField(request, "thumbnailUrl", "https://example.com/cleancode.jpg");

        Book savedBook = Book.create(
                request.getTitle(),
                request.getAuthor(),
                request.getPublisher(),
                request.getIsbn(),
                request.getThumbnailUrl()
        );
        setField(savedBook, "id", 1L); // 저장된 책에 id 설정 (mock용)

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // when
        BookResponse response = bookService.createBook(request);

        // then
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository, times(1)).save(captor.capture());
        Book capturedBook = captor.getValue();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(capturedBook.getTitle()).isEqualTo(request.getTitle());
        assertThat(capturedBook.getAuthor()).isEqualTo(request.getAuthor());
        assertThat(capturedBook.getPublisher()).isEqualTo(request.getPublisher());
        assertThat(capturedBook.getIsbn()).isEqualTo(request.getIsbn());
        assertThat(capturedBook.getThumbnailUrl()).isEqualTo(request.getThumbnailUrl());
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
