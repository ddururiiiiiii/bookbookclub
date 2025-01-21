package seulgi.bookbookclub.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberRequest {

    private String password;
    private String nickName;
    private String info;

}
