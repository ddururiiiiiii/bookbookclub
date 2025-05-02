package ddururi.bookbookclub.domain.book.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class KakaoBookSearchResponse {
    private List<KakaoBookDocument> documents;
}
