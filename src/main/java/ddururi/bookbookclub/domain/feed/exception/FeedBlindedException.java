package ddururi.bookbookclub.domain.feed.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;

/**
 * 블라인드 처리된 피드 접근 시 발생하는 예외
 */
public class FeedBlindedException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.FEED_BLINDED;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
