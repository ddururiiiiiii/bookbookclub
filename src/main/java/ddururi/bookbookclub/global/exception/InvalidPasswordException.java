package ddururi.bookbookclub.global.exception;

import lombok.Getter;

@Getter
public class InvalidPasswordException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD.getMessage());
        this.errorCode = ErrorCode.INVALID_PASSWORD;
    }
}
