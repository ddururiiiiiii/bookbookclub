package ddururi.bookbookclub.domain.follow.dto;

import ddururi.bookbookclub.domain.user.dto.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowResponse {
    private Long followId;
    private UserSummaryResponse user;
}
