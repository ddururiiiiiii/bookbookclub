package toyproject.bookbookclub.domain.Members;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class JoinForm {

    private String id;
    private String NickName;
    private String password;
    private LocalDateTime firstJoinDate;
    private String Bios;
    private MultipartFile profileImage;

}
