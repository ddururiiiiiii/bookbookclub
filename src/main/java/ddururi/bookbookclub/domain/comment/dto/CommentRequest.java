package ddururi.bookbookclub.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 댓글 작성 요청 DTO
 */
@Getter
public class CommentRequest {

    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;

    private Long feedId;
}
