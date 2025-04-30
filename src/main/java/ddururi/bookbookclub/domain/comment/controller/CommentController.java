package ddururi.bookbookclub.domain.comment.controller;

import ddururi.bookbookclub.domain.comment.dto.CommentRequest;
import ddururi.bookbookclub.domain.comment.dto.CommentResponse;
import ddururi.bookbookclub.domain.comment.entity.Comment;
import ddururi.bookbookclub.domain.comment.service.CommentService;
import ddururi.bookbookclub.global.common.ApiResponse;
import ddururi.bookbookclub.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 댓글(Comment) 관련 API 컨트롤러
 * - 댓글 작성, 조회, 삭제
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     * @param userDetails 현재 로그인한 사용자 정보
     * @param request 댓글 작성 요청 데이터
     * @return 생성된 댓글 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CommentRequest request
    ) {
        Comment comment = commentService.createComment(request.getContent(), userDetails.getUser(), request.getFeedId());
        return ResponseEntity.ok(ApiResponse.success(CommentResponse.from(comment)));
    }

    /**
     * 댓글 목록 조회
     * @param feedId 댓글을 조회할 피드 ID
     * @return 댓글 리스트
     */
    @GetMapping("/feed/{feedId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(@PathVariable Long feedId) {
        List<Comment> comments = commentService.getCommentsByFeed(feedId);
        List<CommentResponse> response = comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 댓글 삭제
     * @param commentId 삭제할 댓글 ID
     * @param userDetails 현재 로그인한 사용자 정보
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.success(null, "댓글이 삭제되었습니다."));
    }
}
