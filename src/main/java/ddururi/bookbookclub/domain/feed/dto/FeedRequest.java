package ddururi.bookbookclub.domain.feed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 피드 작성 요청 DTO
 */
@Getter
@NoArgsConstructor
public class FeedRequest {

    @NotBlank(message = "책 제목은 비어 있을 수 없습니다.")
    private String title;

    @NotBlank(message = "책 저자는 비어 있을 수 없습니다.")
    private String author;

    private String publisher;
    private String isbn;
    private String thumbnailUrl;

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    private String content;
}
