package ddururi.bookbookclub.domain.comment.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

/**
 * 댓글 삭제 권한이 없는 경우 발생
 */
@Getter
public class CommentAccessDeniedException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommentAccessDeniedException() {
        super(ErrorCode.COMMENT_ACCESS_DENIED.getMessage());
        this.errorCode = ErrorCode.COMMENT_ACCESS_DENIED;
    }
}
