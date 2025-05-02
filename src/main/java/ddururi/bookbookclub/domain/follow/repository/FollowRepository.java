package ddururi.bookbookclub.domain.follow.repository;

import ddururi.bookbookclub.domain.follow.entity.Follow;
import ddururi.bookbookclub.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerAndFollowing(User follower, User following);

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    List<Follow> findAllByFollower(User follower);

    List<Follow> findAllByFollowing(User following);

    long countByFollower(User follower);

    long countByFollowing(User following);
}
