package ddururi.bookbookclub.domain.feed.service;

import ddururi.bookbookclub.domain.feed.dto.FeedResponse;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.domain.feed.exception.FeedNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

/**
 * 피드 관련 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
@Transactional
public class FeedService {

    private final FeedRepository feedRepository;

    /**
     * 피드 작성
     * @param user 작성자
     * @param content 피드 내용
     * @return 저장된 피드
     */
    public Feed createFeed(User user, String content) {
        Feed feed = Feed.create(user, content); // 수정된 부분!
        return feedRepository.save(feed);
    }

    /**
     * 피드 수정
     * @param feedId 수정할 피드 ID
     * @param newContent 수정할 내용
     * @return 수정된 피드
     * @throws FeedNotFoundException 피드가 존재하지 않을 경우
     */
    public Feed updateFeed(Long feedId, String newContent) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(FeedNotFoundException::new);

        feed.updateContent(newContent);
        return feed;
    }

    /**
     * 피드 삭제
     * @param feedId 삭제할 피드 ID
     * @throws FeedNotFoundException 피드가 존재하지 않을 경우
     */
    public void deleteFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(FeedNotFoundException::new);

        feedRepository.delete(feed);
    }

    /**
     * 피드 단건 조회
     * @param feedId 조회할 피드 ID
     * @return 조회된 피드
     * @throws FeedNotFoundException 피드가 존재하지 않을 경우
     */
    public Feed getFeed(Long feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(FeedNotFoundException::new);
    }

    /**
     * 피드 목록 조회 (페이징, 최신순 정렬)
     * @param pageable 페이징 및 정렬 요청 정보
     * @return 피드 목록 (페이지 형태)
     */
    public Page<FeedResponse> getFeeds(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return feedRepository.findAll(sortedPageable)
                .map(FeedResponse::new);
    }
}
