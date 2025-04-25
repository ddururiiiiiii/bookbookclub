package ddururi.bookbookclub.global.exception;

import lombok.Getter;

@Getter
public class DuplicateNicknameException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateNicknameException() {
        super(ErrorCode.DUPLICATE_NICKNAME.getMessage());
        this.errorCode = ErrorCode.DUPLICATE_NICKNAME;
    }
}
