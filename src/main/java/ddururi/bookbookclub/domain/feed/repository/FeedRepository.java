package ddururi.bookbookclub.domain.feed.repository;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long>{

    /** 블라인드 된 피드 제외하고 전체 조회 (페이징처리) */
    Page<Feed> findByIsBlindedFalse(Pageable pageable);

}
