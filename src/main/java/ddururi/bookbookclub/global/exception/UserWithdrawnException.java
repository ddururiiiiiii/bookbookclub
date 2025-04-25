package ddururi.bookbookclub.global.exception;

import lombok.Getter;

@Getter
public class UserWithdrawnException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserWithdrawnException() {
        super(ErrorCode.USER_WITHDRAWN.getMessage());
        this.errorCode = ErrorCode.USER_WITHDRAWN;
    }
}
