package ddururi.bookbookclub.domain.feed.repository;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 피드 JPA 레포지토리
 */
public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {

    /** 블라인드 되지 않은 피드 페이징 조회 */
    Page<Feed> findByIsBlindedFalse(Pageable pageable);

    /** 블라인드 되지 않은 피드 다건 조회 (id 리스트 기반) */
    List<Feed> findByIdInAndIsBlindedFalse(List<Long> ids);
}
