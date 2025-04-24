package ddururi.bookbookclub.global.exception;

public class RejoinRestrictionException extends RuntimeException {
    public RejoinRestrictionException() {
        super("탈퇴 후 6개월 이내에는 재가입할 수 없습니다.");
    }
}
