package toyproject.bookbookclub.domain.Members;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MemberJoinForm {

    private String id;
    private String NickName;
    private String password;
    private MultipartFile profileImage;

}
