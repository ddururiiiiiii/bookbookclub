package ddururi.bookbookclub.domain.follow.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AccessDeniedException extends RuntimeException {
    private final ErrorCode errorCode;

    public AccessDeniedException(String message) {
        super(message);
        this.errorCode = ErrorCode.ACCESS_DENIED;
    }
}