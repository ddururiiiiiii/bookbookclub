package ddururi.bookbookclub.domain.book.service;

import ddururi.bookbookclub.domain.book.dto.BookRequest;
import ddururi.bookbookclub.domain.book.dto.BookResponse;
import ddururi.bookbookclub.domain.book.entity.Book;
import ddururi.bookbookclub.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 책 관련 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 책 등록
     * @param request 책 등록 요청 데이터
     * @return 등록된 책 정보
     */
    public BookResponse createBook(BookRequest request) {
        Book book = Book.create(
                request.getTitle(),
                request.getAuthor(),
                request.getPublisher(),
                request.getIsbn(),
                request.getThumbnailUrl()
        );
        Book savedBook = bookRepository.save(book);
        return new BookResponse(savedBook);
    }
}
