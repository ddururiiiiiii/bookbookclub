package seulgi.bookbookclub.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
public class CreateMemberResponse {

    private Integer memberSeq;

    public CreateMemberResponse(Integer memberSeq) {
        this.memberSeq = memberSeq;
    }
}
