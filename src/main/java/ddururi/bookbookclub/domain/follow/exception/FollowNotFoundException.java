package ddururi.bookbookclub.domain.follow.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class FollowNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public FollowNotFoundException() {
        super(ErrorCode.FOLLOW_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.FOLLOW_NOT_FOUND;
    }
}