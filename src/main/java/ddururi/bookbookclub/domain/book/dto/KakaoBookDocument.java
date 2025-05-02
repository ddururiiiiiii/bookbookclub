package ddururi.bookbookclub.domain.book.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class KakaoBookDocument {
    private String title;
    private List<String> authors;
    private String publisher;
    private String isbn;
    private String thumbnail;
}
