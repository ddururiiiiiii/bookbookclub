package ddururi.bookbookclub.domain.feed.controller;

import ddururi.bookbookclub.domain.feed.dto.FeedRequest;
import ddururi.bookbookclub.domain.feed.dto.FeedResponse;
import ddururi.bookbookclub.domain.feed.dto.FeedUpdateRequest;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.service.FeedService;
import ddururi.bookbookclub.domain.feed.service.RankingFeedService;
import ddururi.bookbookclub.global.common.ApiResponse;
import ddururi.bookbookclub.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 피드 관련 API 컨트롤러
 * - 작성, 수정, 삭제, 단건 조회, 목록 조회
 */
@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final RankingFeedService rankingFeedService;

    /**
     * 피드 작성
     * @param userDetails 현재 로그인한 사용자 정보
     * @param request 피드 작성 요청 데이터
     * @return 생성된 피드 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse<FeedResponse>> createFeed(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid FeedRequest request
    ) {
        Feed feed = feedService.createFeed(userDetails.getUser(), request);
        return ResponseEntity.ok(ApiResponse.success(new FeedResponse(feed, 0L, false)));
    }


    /**
     * 피드 수정
     * @param feedId 수정할 피드 ID
     * @param request 수정할 피드 내용
     * @return 수정된 피드 정보
     */
    @PutMapping("/{feedId}")
    public ResponseEntity<ApiResponse<FeedResponse>> updateFeed(
            @PathVariable Long feedId,
            @RequestBody @Valid FeedUpdateRequest request
    ) {
        Feed feed = feedService.updateFeed(feedId, request.getContent());
        return ResponseEntity.ok(ApiResponse.success(new FeedResponse(feed, 0L, false)));
    }

    /**
     * 피드 삭제
     * @param feedId 삭제할 피드 ID
     */
    @DeleteMapping("/{feedId}")
    public ResponseEntity<ApiResponse<Void>> deleteFeed(@PathVariable Long feedId) {
        feedService.deleteFeed(feedId);
        return ResponseEntity.ok(ApiResponse.success(null, "피드가 삭제되었습니다."));
    }

    /**
     * 피드 단건 조회 (좋아요 수, 좋아요 여부 포함)
     * @param feedId 조회할 피드 ID
     * @param userDetails 현재 로그인한 사용자 정보
     * @return 조회된 피드 정보
     */
    @GetMapping("/{feedId}")
    public ResponseEntity<ApiResponse<FeedResponse>> getFeed(
            @PathVariable Long feedId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        FeedResponse feedResponse = feedService.getFeed(feedId, userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponse.success(feedResponse));
    }

    /**
     * 피드 목록 조회 (페이징, 좋아요 수, 좋아요 여부 포함)
     * @param pageable 페이징 요청 정보
     * @param userDetails 현재 로그인한 사용자 정보
     * @return 피드 목록 (페이지 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<FeedResponse>>> getFeeds(
            Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Page<FeedResponse> feeds = feedService.getFeeds(pageable, userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponse.success(feeds));
    }

    /**
     * 좋아요 랭킹 조회 API
     * @param period weekly, monthly, yearly, total
     * @param topN 가져올 피드 수 (기본값 10)
     * @return 좋아요 TOP N 피드 ID 리스트
     */
    @GetMapping("/popular/like")
    public ApiResponse<?> getPopularByLike(
            @RequestParam String period,
            @RequestParam(defaultValue = "10") int topN) {
        return ApiResponse.success(rankingFeedService.getTopLikedFeeds(period, topN));
    }

}
