package ddururi.bookbookclub.domain.user.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("활동 중"),
    WITHDRAWN("탈퇴");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }
}
