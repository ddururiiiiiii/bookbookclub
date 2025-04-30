package ddururi.bookbookclub.domain.book.dto;

import ddururi.bookbookclub.domain.book.entity.Book;
import lombok.Getter;

@Getter
public class BookResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final String publisher;
    private final String isbn;
    private final String thumbnailUrl;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.isbn = book.getIsbn();
        this.thumbnailUrl = book.getThumbnailUrl();
    }
}
