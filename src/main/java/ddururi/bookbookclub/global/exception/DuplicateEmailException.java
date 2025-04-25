package ddururi.bookbookclub.global.exception;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL.getMessage());
        this.errorCode = ErrorCode.DUPLICATE_EMAIL;
    }
}
