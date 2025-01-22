package seulgi.bookbookclub.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;

@Data
@AllArgsConstructor
public class TimelineDto {

    private Integer timelineSeq;
    private String memberId;
    private String nickName;
    private Integer likes;
    private String bookTitle;
    private String bookAuthor;
    private String publisher;
    private LocalDateTime createdDate;

}
