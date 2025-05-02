package ddururi.bookbookclub.domain.feed.repository;

import ddururi.bookbookclub.domain.feed.entity.Feed;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 피드 QueryDSL 커스텀 레포지토리 인터페이스
 */
public interface FeedRepositoryCustom {

    /** 키워드 검색 기반 피드 조회 */
    List<Feed> searchFeeds(String keyword, Pageable pageable);

    /** 특정 회원이 작성한 피드 목록 조회 */
    List<Feed> findFeedsByUserId(Long userId, Pageable pageable);

}
