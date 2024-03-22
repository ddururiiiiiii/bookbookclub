package toyproject.bookbookclub.domain.Timeline;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import toyproject.bookbookclub.domain.UploadFile;

import java.time.LocalDateTime;

@Getter @Setter
public class Timeline {

    private String timelineId;

//    @NotEmpty
    private String bookId;

    private UploadFile bookImg;

//    @NotEmpty
    private String memberId;

//    @NotEmpty
    private String content;
    private LocalDateTime lastUpdateDate;

    public Timeline() {
    }

    public Timeline(String timelineId, String bookId, UploadFile bookImg, String memberId, String content, LocalDateTime lastUpdateDate) {
        this.timelineId = timelineId;
        this.bookId = bookId;
        this.bookImg = bookImg;
        this.memberId = memberId;
        this.content = content;
        this.lastUpdateDate = lastUpdateDate;
    }
}
