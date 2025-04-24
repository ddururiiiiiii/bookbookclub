package ddururi.bookbookclub.domain.user.entity;

import ddururi.bookbookclub.domain.user.enums.AuthProvider;
import ddururi.bookbookclub.domain.user.enums.Role;
import ddururi.bookbookclub.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Setter
    @Column(unique = true, nullable = false)
    private String nickname;

    @Setter
    @Column(length = 1000)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Setter
    @Column(length = 500)
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    @Column(nullable = false)
    private String providerId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime withdrawnAt;

    // 정적 생성 메서드
    public static User create(String email, String encodedPassword, String nickname) {
        User user = new User();
        user.email = email;
        user.password = encodedPassword;
        user.nickname = nickname;
        user.role = Role.USER;
        user.status = UserStatus.ACTIVE;
        user.provider = AuthProvider.LOCAL;
        user.providerId = "LOCAL";
        return user;
    }

    public static User createSocialUser(String email, String nickname, AuthProvider provider, String providerId) {
        User user = new User();
        user.email = email;
        user.password = "oauth2"; // 의미 없는 값
        user.nickname = nickname;
        user.role = Role.USER;
        user.status = UserStatus.ACTIVE;
        user.provider = provider;
        user.providerId = providerId;
        return user;
    }

    public void withdraw() {
        this.status = UserStatus.WITHDRAWN;
        this.withdrawnAt = LocalDateTime.now();
    }
}
