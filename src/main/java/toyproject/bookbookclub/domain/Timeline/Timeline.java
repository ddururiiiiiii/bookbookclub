package toyproject.bookbookclub.domain.Timeline;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Timeline {

    private String timelineId;
    private String bookId;
    private String bookImg;
    private String memberId;
    private String content;
    private String lastUpdateDate;

    public Timeline() {
    }


    //save
    public Timeline(String timelineId, String bookId, String bookImg, String memberId, String content, String lastUpdateDate) {
        this.timelineId = timelineId;
        this.bookId = bookId;
        this.bookImg = bookImg;
        this.memberId = memberId;
        this.content = content;
        this.lastUpdateDate = lastUpdateDate;
    }

}
