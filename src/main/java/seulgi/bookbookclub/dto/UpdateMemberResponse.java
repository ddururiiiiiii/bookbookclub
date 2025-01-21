package seulgi.bookbookclub.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberResponse {

    private Integer memberSeq;
    private String Nickname;
    private String info;

}
