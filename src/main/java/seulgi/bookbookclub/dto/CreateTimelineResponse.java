package seulgi.bookbookclub.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTimelineResponse {

    private Integer timelineSeq;

    public CreateTimelineResponse(Integer timelineSeq) {
        this.timelineSeq = timelineSeq;
    }
}
