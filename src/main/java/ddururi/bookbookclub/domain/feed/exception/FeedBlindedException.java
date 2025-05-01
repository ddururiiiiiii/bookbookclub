package ddururi.bookbookclub.domain.feed.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;

public class FeedBlindedException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.FEED_BLINDED;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
