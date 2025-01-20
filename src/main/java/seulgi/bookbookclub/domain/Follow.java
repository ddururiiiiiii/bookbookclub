package seulgi.bookbookclub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class) // Auditing 활성화
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followSeq;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "follower_seq", nullable = false)
    private Member follower; // 팔로잉 하는 회원 (FK)

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "following_seq", nullable = false)
    private Member following; // 팔로잉 당하는 회원 (FK)

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    public Follow(Member follower, Member following) {
        this.follower = follower;
        this.following = following;
    }

}