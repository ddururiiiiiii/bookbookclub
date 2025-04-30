package ddururi.bookbookclub.domain.like.controller;

import ddururi.bookbookclub.domain.like.service.LikeService;
import ddururi.bookbookclub.domain.user.dto.UserSummaryResponse;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.global.common.ApiResponse;
import ddururi.bookbookclub.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 좋아요(Like) API 컨트롤러
 */
@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    /**
     * 좋아요 토글 API
     * - 이미 좋아요가 되어 있으면 좋아요를 취소
     * - 좋아요가 안 되어 있으면 좋아요 추가
     *
     * @param feedId 좋아요를 토글할 피드 ID
     * @param userDetails 인증된 사용자 정보
     * @return 좋아요 상태에 따른 메시지 응답
     */
    @PostMapping("/{feedId}/likes")
    public ResponseEntity<ApiResponse<String>> toggleLike(@PathVariable Long feedId,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        boolean liked = likeService.toggleLike(user, feedId);
        String message = liked ? "좋아요를 눌렀습니다." : "좋아요를 취소했습니다.";
        return ResponseEntity.ok(ApiResponse.success(message));
    }


    /**
     * 피드에 좋아요한 사용자 목록 조회
     * @param feedId 피드 ID
     * @return 사용자 요약 정보 리스트
     */
    @GetMapping("/{feedId}/likes/users")
    public ResponseEntity<ApiResponse<List<UserSummaryResponse>>> getUsersWhoLikedFeed(@PathVariable Long feedId) {
        List<UserSummaryResponse> users = likeService.getUsersWhoLikedFeed(feedId);
        return ResponseEntity.ok(ApiResponse.success(users));
    }
}
