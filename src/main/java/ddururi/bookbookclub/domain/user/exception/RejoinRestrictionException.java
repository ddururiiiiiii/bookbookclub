package ddururi.bookbookclub.domain.user.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;
import lombok.Getter;

/**
 * 탈퇴 후 재가입 제한 기간 내 시도 시 발생
 */
@Getter
public class RejoinRestrictionException extends RuntimeException {
    private final ErrorCode errorCode;

    public RejoinRestrictionException() {
        super(ErrorCode.REJOIN_RESTRICTED.getMessage());
        this.errorCode = ErrorCode.REJOIN_RESTRICTED;
    }
}
