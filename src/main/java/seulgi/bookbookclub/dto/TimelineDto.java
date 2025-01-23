package seulgi.bookbookclub.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TimelineDto {

    private Integer timelineSeq;
    private String memberId;
    private String nickName;
    private String contents;
    private Integer likes;
    private String bookTitle;
    private String bookAuthor;
    private String publisher;
    private LocalDateTime createdDate;

}
