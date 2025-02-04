package seulgi.bookbookclub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberSeq;

    @Column(length = 180, nullable = false, unique = true)
    private String memberId;

    @Column(length = 300, nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(length = 300)
    private String profileImage;

    @Column(length = 600)
    private String info;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private State state = State.ACTIVE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_date;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime last_modified_date;



    public Member(String memberId, String password, String nickname, String info) {
        this.memberId = memberId;
        this.password = password;
        this.nickname = nickname;
        this.info = info;
    }

    public Member(Member member, String password, String nickname, String info) {
        this.memberSeq = member.getMemberSeq();
        this.memberId = member.getMemberId();
        this.password = password;
        this.nickname = nickname;
        this.info = info;
    }


}
