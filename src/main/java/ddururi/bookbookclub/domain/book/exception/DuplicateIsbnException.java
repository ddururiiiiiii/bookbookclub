package ddururi.bookbookclub.domain.book.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

/**
 * 중복 ISBN 예외
 * - 책 등록 시 중복 ISBN 검사에 사용
 */
@Getter
public class DuplicateIsbnException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateIsbnException() {
        super(ErrorCode.DUPLICATE_ISBN.getMessage());
        this.errorCode = ErrorCode.DUPLICATE_ISBN;
    }
}
