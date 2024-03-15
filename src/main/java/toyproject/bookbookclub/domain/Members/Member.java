package toyproject.bookbookclub.domain.Members;

import lombok.Getter;
import lombok.Setter;
import toyproject.bookbookclub.domain.UploadFile;

@Getter @Setter
public class Member {

    private String id;
    private String NickName;
    private String password;
    private UploadFile profileImage;

    public Member() {
    }

    public Member(String id, String nickName, String password, UploadFile profileImage) {
        this.id = id;
        this.NickName = nickName;
        this.password = password;
        this.profileImage = profileImage;
    }

    public Member(String id, String nickName, String password) {
        this.id = id;
        NickName = nickName;
        this.password = password;
    }

}
