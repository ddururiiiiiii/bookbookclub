package seulgi.bookbookclub.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMemberRequest {

    private String memberId;
    private String name;
    private String nickName;
    private String info;

}
