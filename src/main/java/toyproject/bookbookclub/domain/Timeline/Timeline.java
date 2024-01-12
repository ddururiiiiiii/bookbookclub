package toyproject.bookbookclub.domain.Timeline;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Timeline {

    private String timelimeId;
    private String bookId;
    private String bookImg;
    private String memberId;
    private String content;
    private String lastUpdateDate;

    public Timeline() {
    }


    //save
    public Timeline(String timelimeId, String bookId, String bookImg, String memberId, String content, String lastUpdateDate) {
        this.timelimeId = timelimeId;
        this.bookId = bookId;
        this.bookImg = bookImg;
        this.memberId = memberId;
        this.content = content;
        this.lastUpdateDate = lastUpdateDate;
    }

}
