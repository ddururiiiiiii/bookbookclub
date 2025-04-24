package ddururi.bookbookclub.domain.user.dto;

import lombok.Getter;

@Getter
public class RefreshTokenRequest {
    private Long userId;
    private String refreshToken;
}

