package ddururi.bookbookclub.domain.comment.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

/**
 * 존재하지 않는 댓글에 접근할 때 발생
 */
@Getter
public class CommentNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.COMMENT_NOT_FOUND;
    }
}
