package ddururi.bookbookclub.global.exception;

import lombok.Getter;

@Getter
public class EmailNotVerifiedException extends RuntimeException {
    private final ErrorCode errorCode;

    public EmailNotVerifiedException() {
        super(ErrorCode.EMAIL_NOT_VERIFIED.getMessage());
        this.errorCode = ErrorCode.EMAIL_NOT_VERIFIED;
    }
}
