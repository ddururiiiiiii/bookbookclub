package ddururi.bookbookclub.domain.like.repository;

import ddururi.bookbookclub.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 좋아요(Like) 관련 데이터 액세스 레포지토리
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndFeedId(Long userId, Long feedId);

    Optional<Like> findByUserIdAndFeedId(Long userId, Long feedId);

    long countByFeedId(Long feedId);

    List<Like> findAllByFeedId(Long feedId);
}
