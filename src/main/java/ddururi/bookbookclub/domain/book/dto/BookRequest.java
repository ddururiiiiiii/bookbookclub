package ddururi.bookbookclub.domain.book.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 책 등록 요청 DTO
 */
@Getter
@NoArgsConstructor
public class BookRequest {

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    private String title;

    @NotBlank(message = "저자는 비어 있을 수 없습니다.")
    private String author;

    private String publisher;
    private String isbn;
    private String thumbnailUrl;
}
