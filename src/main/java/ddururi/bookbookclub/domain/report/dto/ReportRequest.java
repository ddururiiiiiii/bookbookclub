package ddururi.bookbookclub.domain.report.dto;

import ddururi.bookbookclub.domain.report.enums.ReasonType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 피드 신고 요청 DTO
 */
@Getter
@NoArgsConstructor
public class ReportRequest {

    /** 신고 사유 */
    @NotNull(message = "신고 사유는 필수입니다.")
    private ReasonType reason;
}
