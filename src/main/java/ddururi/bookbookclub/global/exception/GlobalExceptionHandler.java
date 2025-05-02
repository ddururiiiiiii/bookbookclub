package ddururi.bookbookclub.global.exception;

import ddururi.bookbookclub.domain.book.exception.DuplicateIsbnException;
import ddururi.bookbookclub.domain.comment.exception.CommentAccessDeniedException;
import ddururi.bookbookclub.domain.comment.exception.CommentNotFoundException;
import ddururi.bookbookclub.domain.emailverification.exception.EmailNotVerifiedException;
import ddururi.bookbookclub.domain.emailverification.exception.EmailVerificationLimitExceededException;
import ddururi.bookbookclub.domain.feed.exception.FeedBlindedException;
import ddururi.bookbookclub.domain.feed.exception.FeedNotFoundException;
import ddururi.bookbookclub.domain.follow.exception.AccessDeniedException;
import ddururi.bookbookclub.domain.follow.exception.AlreadyFollowingException;
import ddururi.bookbookclub.domain.follow.exception.FollowNotFoundException;
import ddururi.bookbookclub.domain.like.exception.LikeException;
import ddururi.bookbookclub.domain.report.exception.AlreadyReportedException;
import ddururi.bookbookclub.domain.user.exception.*;
import ddururi.bookbookclub.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리 핸들러
 * - 각 예외 상황에 맞는 HTTP 상태코드 및 응답 구조 반환
 * - ApiResponse.fail(...) 구조 사용
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailNotVerified(EmailNotVerifiedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(UserWithdrawnException.class)
    public ResponseEntity<ApiResponse<Void>> handleWithdrawn(UserWithdrawnException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPassword(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(RejoinRestrictionException.class)
    public ResponseEntity<ApiResponse<Void>> handleRejoinRestricted(RejoinRestrictionException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEmail(DuplicateEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateNickname(DuplicateNicknameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidToken(InvalidTokenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(EmailVerificationLimitExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailVerificationLimit(EmailVerificationLimitExceededException e) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS) // 429
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(FeedNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleFeedNotFound(FeedNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(LikeException.class)
    public ResponseEntity<ApiResponse<Void>> handleLikeException(LikeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCommentNotFound(CommentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(CommentAccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleCommentAccessDenied(CommentAccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(AlreadyReportedException.class)
    public ResponseEntity<ApiResponse<?>> handleAlreadyReportedException(AlreadyReportedException ex) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.fail(ErrorCode.ALREADY_REPORTED));
    }

    @ExceptionHandler(FeedBlindedException.class)
    public ResponseEntity<ApiResponse<Void>> handleFeedBlinded(FeedBlindedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllException(Exception ex) {
        ex.printStackTrace(); // 운영환경에서는 로거로 변경
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(ErrorCode.INTERNAL_SERVER_ERROR));
    }
    @ExceptionHandler(AlreadyFollowingException.class)
    public ResponseEntity<ApiResponse<Void>> handleAlreadyFollowing(AlreadyFollowingException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(FollowNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleFollowNotFound(FollowNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateIsbn(DuplicateIsbnException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.fail(e.getErrorCode()));
    }

}
