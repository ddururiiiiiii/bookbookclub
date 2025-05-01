package ddururi.bookbookclub.domain.report.exception;

import ddururi.bookbookclub.global.exception.ErrorCode;

/**
 * 이미 신고한 피드에 대해 다시 신고할 경우 발생하는 예외
 */
public class AlreadyReportedException extends RuntimeException {
    private final ErrorCode errorCode = ErrorCode.ALREADY_REPORTED;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
