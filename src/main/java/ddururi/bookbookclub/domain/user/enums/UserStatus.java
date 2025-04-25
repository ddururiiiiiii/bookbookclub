package ddururi.bookbookclub.domain.user.enums;

import lombok.Getter;
/**
 * 사용자 상태 열거형
 * - ACTIVE: 정상 활동 중
 * - WITHDRAWN: 탈퇴 처리됨 (논리 삭제)
 */
@Getter
public enum UserStatus {
    ACTIVE("활동 중"),
    WITHDRAWN("탈퇴");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }
}
