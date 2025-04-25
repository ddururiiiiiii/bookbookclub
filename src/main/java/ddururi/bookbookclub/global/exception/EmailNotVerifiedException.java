package ddururi.bookbookclub.global.exception;

import lombok.Getter;

/**
 * 이메일 인증 미완료 예외
 * - 회원가입 시 인증되지 않은 경우 발생
 */
@Getter
public class EmailNotVerifiedException extends RuntimeException {
    private final ErrorCode errorCode;

    public EmailNotVerifiedException() {
        super(ErrorCode.EMAIL_NOT_VERIFIED.getMessage());
        this.errorCode = ErrorCode.EMAIL_NOT_VERIFIED;
    }
}
