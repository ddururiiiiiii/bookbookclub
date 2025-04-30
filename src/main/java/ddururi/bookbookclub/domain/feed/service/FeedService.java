package ddururi.bookbookclub.domain.feed.service;

import ddururi.bookbookclub.domain.book.entity.Book;
import ddururi.bookbookclub.domain.book.repository.BookRepository;
import ddururi.bookbookclub.domain.feed.dto.FeedRequest;
import ddururi.bookbookclub.domain.feed.dto.FeedResponse;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import ddururi.bookbookclub.domain.like.service.LikeService;
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
    private final LikeService likeService;
    private final BookRepository bookRepository;

    /**
     * 피드 작성
     * @param user 작성자
     * @param request 피드 작성 요청 데이터
     * @return 저장된 피드
     */
    public Feed createFeed(User user, FeedRequest request) {
        Book book = findOrCreateBook(request);
        Feed feed = Feed.create(user, book, request.getContent());
        return feedRepository.save(feed);
    }

    /**
     * 책을 찾거나, 없으면 새로 생성
     */
    private Book findOrCreateBook(FeedRequest request) {
        // 1. ISBN이 존재하면 ISBN으로 먼저 찾기
        if (request.getIsbn() != null && !request.getIsbn().isBlank()) {
            return bookRepository.findByIsbn(request.getIsbn())
                    .orElseGet(() -> createNewBook(request));
        }

        // 2. ISBN이 없으면 title + author로 찾기
        return bookRepository.findByTitleAndAuthor(request.getTitle(), request.getAuthor())
                .orElseGet(() -> createNewBook(request));
    }

    /**
     * 새 책 생성
     */
    private Book createNewBook(FeedRequest request) {
        Book newBook = Book.create(
                request.getTitle(),
                request.getAuthor(),
                request.getPublisher(),
                request.getIsbn(),
                request.getThumbnailUrl()
        );
        return bookRepository.save(newBook);
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
     *
     * @param feedId 조회할 피드 ID
     * @return 조회된 피드
     * @throws FeedNotFoundException 피드가 존재하지 않을 경우
     */
    public FeedResponse getFeed(Long feedId, Long userId) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(FeedNotFoundException::new);
        long likeCount = likeService.getLikeCount(feedId);
        boolean liked = likeService.hasUserLiked(userId, feedId);
        return new FeedResponse(feed, likeCount, liked);

    }

    /**
     * 피드 목록 조회 (페이징, 좋아요 수, 좋아요 여부 포함)
     * @param pageable 페이징 요청 정보
     * @param userId 현재 사용자 ID
     * @return 피드 목록 (페이지 형태)
     */
    @Transactional
    public Page<FeedResponse> getFeeds(Pageable pageable, Long userId) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return feedRepository.findAll(sortedPageable)
                .map(feed -> {
                    long likeCount = likeService.getLikeCount(feed.getId());
                    boolean liked = likeService.hasUserLiked(userId, feed.getId());
                    return new FeedResponse(feed, likeCount, liked);
                });
    }
}
