package ddururi.bookbookclub.global.exception;

import lombok.Getter;

@Getter
public class EmailVerificationLimitExceededException extends RuntimeException {
    private final ErrorCode errorCode;

    public EmailVerificationLimitExceededException() {
        super(ErrorCode.EMAIL_VERIFICATION_TOO_MANY_ATTEMPTS.getMessage());
        this.errorCode = ErrorCode.EMAIL_VERIFICATION_TOO_MANY_ATTEMPTS;
    }
}
