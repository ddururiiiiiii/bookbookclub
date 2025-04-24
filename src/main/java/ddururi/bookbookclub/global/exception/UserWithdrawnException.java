package ddururi.bookbookclub.global.exception;

public class UserWithdrawnException extends RuntimeException {
    public UserWithdrawnException() {
        super("탈퇴한 사용자입니다.");
    }
}
