package ddururi.bookbookclub.domain.like.controller;

import ddururi.bookbookclub.domain.like.service.LikeService;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.global.common.ApiResponse;
import ddururi.bookbookclub.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 좋아요(Like) API 컨트롤러
 */
@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    /**
     * 피드 좋아요
     * @param feedId 좋아요할 피드 ID
     * @param userDetails 인증된 사용자 정보
     */
    @PostMapping("/{feedId}/likes")
    public ResponseEntity<ApiResponse<Void>> like(@PathVariable Long feedId,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        likeService.likeFeed(user, feedId);
        return ResponseEntity.ok(ApiResponse.success(null, "좋아요를 눌렀습니다."));
    }

    /**
     * 피드 좋아요 취소
     * @param feedId 좋아요 취소할 피드 ID
     * @param userDetails 인증된 사용자 정보
     */
    @DeleteMapping("/{feedId}/likes")
    public ResponseEntity<ApiResponse<Void>> unlike(@PathVariable Long feedId,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        likeService.unlikeFeed(user, feedId);
        return ResponseEntity.ok(ApiResponse.success(null, "좋아요를 취소했습니다."));
    }
}
