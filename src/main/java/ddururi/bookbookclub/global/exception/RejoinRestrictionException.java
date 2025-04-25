package ddururi.bookbookclub.global.exception;

import lombok.Getter;

@Getter
public class RejoinRestrictionException extends RuntimeException {
    private final ErrorCode errorCode;

    public RejoinRestrictionException() {
        super(ErrorCode.REJOIN_RESTRICTED.getMessage());
        this.errorCode = ErrorCode.REJOIN_RESTRICTED;
    }
}
