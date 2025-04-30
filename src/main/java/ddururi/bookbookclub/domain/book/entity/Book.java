package ddururi.bookbookclub.domain.book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 책(Book) 엔티티
 * - 외부 책 API에서 검색된 책 정보를 저장
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 책 제목 */
    @Column(nullable = false)
    private String title;

    /** 저자 */
    private String author;

    /** 출판사 */
    private String publisher;

    /** ISBN */
    @Column(unique = true)
    private String isbn;

    /** 썸네일 이미지 URL */
    @Column(length = 1000)
    private String thumbnailUrl;

    /**
     * Book 생성 정적 메서드
     */
    public static Book create(String title, String author, String publisher, String isbn, String thumbnailUrl) {
        Book book = new Book();
        book.title = title;
        book.author = author;
        book.publisher = publisher;
        book.isbn = isbn;
        book.thumbnailUrl = thumbnailUrl;
        return book;
    }
}
