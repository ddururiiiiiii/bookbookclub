package ddururi.bookbookclub.global.exception;

import ddururi.bookbookclub.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllException(Exception ex) {
        ex.printStackTrace(); // 에러 로그 출력 (실운영 시 로깅 처리 권장)
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("알 수 없는 서버 오류가 발생했습니다."));
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailNotVerified(EmailNotVerifiedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(UserWithdrawnException.class)
    public ResponseEntity<ApiResponse<Void>> handleWithdrawn(UserWithdrawnException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPassword(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(RejoinRestrictionException.class)
    public ResponseEntity<ApiResponse<Void>> handleRejoinRestricted(RejoinRestrictionException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.fail(e.getMessage()));
    }
}