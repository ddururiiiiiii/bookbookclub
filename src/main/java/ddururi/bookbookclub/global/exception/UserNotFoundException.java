package ddururi.bookbookclub.global.exception;

import lombok.Getter;

/**
 * 사용자 정보를 찾을 수 없을 때 발생
 */
@Getter
public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.USER_NOT_FOUND;
    }
}
