package ddururi.bookbookclub.global.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidTokenException() {
        super(ErrorCode.INVALID_EMAIL_VERIFICATION_TOKEN.getMessage());
        this.errorCode = ErrorCode.INVALID_EMAIL_VERIFICATION_TOKEN;
    }
}
