package ddururi.bookbookclub.domain.feed.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

/**
 * 존재하지 않는 피드에 접근할 때 발생
 */
@Getter
public class FeedNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public FeedNotFoundException() {
        super(ErrorCode.FEED_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.FEED_NOT_FOUND;
    }
}
