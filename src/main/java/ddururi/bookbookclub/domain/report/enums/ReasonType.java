package ddururi.bookbookclub.domain.report.enums;

import lombok.Getter;

/**
 * 신고 사유 열거형
 * - SPAM: 스팸/홍보
 * - OFFENSIVE_LANGUAGE: 욕설/모욕
 * - INAPPROPRIATE_CONTENT: 부적절한 콘텐츠
 * - OTHER: 기타
 */
@Getter
public enum ReasonType {

    SPAM("스팸/홍보"),
    OFFENSIVE_LANGUAGE("욕설/모욕"),
    INAPPROPRIATE_CONTENT("부적절한 콘텐츠"),
    OTHER("기타");

    private final String description;

    ReasonType(String description) {
        this.description = description;
    }
}
