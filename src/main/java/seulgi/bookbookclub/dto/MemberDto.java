package seulgi.bookbookclub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {

    private String memberId;
    private String nickName;
    private String info;

}
