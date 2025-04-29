package ddururi.bookbookclub.domain.user.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

/**
 * 탈퇴한 사용자가 로그인 시도할 때 발생
 */
@Getter
public class UserWithdrawnException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserWithdrawnException() {
        super(ErrorCode.USER_WITHDRAWN.getMessage());
        this.errorCode = ErrorCode.USER_WITHDRAWN;
    }
}
