package ddururi.bookbookclub.global.exception;

import lombok.Getter;

/**
 * 중복 이메일 예외
 * - 회원가입 시 중복 이메일 검사에 사용
 */
@Getter
public class DuplicateEmailException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL.getMessage());
        this.errorCode = ErrorCode.DUPLICATE_EMAIL;
    }
}
