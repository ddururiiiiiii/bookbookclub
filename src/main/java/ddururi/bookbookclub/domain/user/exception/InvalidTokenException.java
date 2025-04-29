package ddururi.bookbookclub.domain.user.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

/**
 * 잘못된 또는 만료된 인증 토큰 예외
 */
@Getter
public class InvalidTokenException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidTokenException() {
        super(ErrorCode.INVALID_EMAIL_VERIFICATION_TOKEN.getMessage());
        this.errorCode = ErrorCode.INVALID_EMAIL_VERIFICATION_TOKEN;
    }
}
