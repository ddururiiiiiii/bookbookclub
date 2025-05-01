package ddururi.bookbookclub.domain.report.controller;

import ddururi.bookbookclub.domain.report.dto.ReportRequest;
import ddururi.bookbookclub.domain.report.service.ReportService;
import ddururi.bookbookclub.global.common.ApiResponse;
import ddururi.bookbookclub.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 피드 신고 API 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds/{feedId}/reports")
public class ReportController {

    private final ReportService reportService;

    /** 피드 신고 요청 API */
    @PostMapping
    public ApiResponse<Void> reportFeed(
            @PathVariable Long feedId,
            @RequestBody @Valid ReportRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        reportService.reportFeed(feedId, userDetails.getUser().getId(), request.getReason());
        return ApiResponse.success(null, "신고가 정상적으로 접수되었습니다.");

    }
}
