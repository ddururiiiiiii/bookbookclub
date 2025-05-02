package ddururi.bookbookclub.domain.follow.controller;

import ddururi.bookbookclub.domain.follow.dto.FollowActionResponse;
import ddururi.bookbookclub.domain.follow.dto.FollowResponse;
import ddururi.bookbookclub.domain.follow.exception.AccessDeniedException;
import ddururi.bookbookclub.domain.follow.service.FollowService;
import ddururi.bookbookclub.global.common.ApiResponse;
import ddururi.bookbookclub.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{targetId}")
    public ResponseEntity<ApiResponse<FollowActionResponse>> follow(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long userId,
            @PathVariable Long targetId) {

        validateUserAccess(userDetails, userId);
        FollowActionResponse response = followService.follow(userId, targetId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<ApiResponse<FollowActionResponse>> unfollow(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long userId,
            @PathVariable Long targetId) {

        validateUserAccess(userDetails, userId);
        FollowActionResponse response = followService.unfollow(userId, targetId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/followers")
    public ResponseEntity<ApiResponse<List<FollowResponse>>> getFollowers(@PathVariable Long userId) {
        List<FollowResponse> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(ApiResponse.success(followers));
    }

    @GetMapping("/followings")
    public ResponseEntity<ApiResponse<List<FollowResponse>>> getFollowings(@PathVariable Long userId) {
        List<FollowResponse> followings = followService.getFollowings(userId);
        return ResponseEntity.ok(ApiResponse.success(followings));
    }

    private void validateUserAccess(CustomUserDetails userDetails, Long userId) {
        if (!userDetails.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("본인 계정으로만 요청할 수 있습니다.");
        }
    }
}

