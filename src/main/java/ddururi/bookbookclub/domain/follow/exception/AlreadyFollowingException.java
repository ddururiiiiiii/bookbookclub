package ddururi.bookbookclub.domain.follow.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AlreadyFollowingException extends RuntimeException {
    private final ErrorCode errorCode;

    public AlreadyFollowingException() {
        super(ErrorCode.ALREADY_FOLLOWING.getMessage());
        this.errorCode = ErrorCode.ALREADY_FOLLOWING;
    }
}
