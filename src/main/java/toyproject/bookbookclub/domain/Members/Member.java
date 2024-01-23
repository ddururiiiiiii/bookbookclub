package toyproject.bookbookclub.domain.Members;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {
//    @Data 를 어노테이션으로 쓰지 않는 이유

    private String id;
    private String NickName;
    private String password;

    // cmd + n을 통해 생성자, getter, setter 생성가능
    // 기본 생성자와 아이디를 제외한 생성자 두개만 만들어 줌.
    public Member() {
    }


    public Member(String id, String nickName, String password) {
        this.id = id;
        NickName = nickName;
        this.password = password;
    }

}
