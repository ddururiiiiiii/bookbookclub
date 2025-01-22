package seulgi.bookbookclub.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import seulgi.bookbookclub.domain.Book;

@Getter
@Setter
public class CreateTimelineRequest {

    private Integer memberSeq;
    private Book book;
    private String contents;

}
