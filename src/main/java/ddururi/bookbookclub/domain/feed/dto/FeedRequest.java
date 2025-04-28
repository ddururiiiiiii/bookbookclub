package ddururi.bookbookclub.domain.feed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedRequest {

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    private String content;
}
