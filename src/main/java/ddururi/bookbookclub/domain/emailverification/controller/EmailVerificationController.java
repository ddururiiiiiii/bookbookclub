package ddururi.bookbookclub.domain.emailverification.controller;

import ddururi.bookbookclub.domain.emailverification.service.EmailVerificationService;
import ddururi.bookbookclub.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/verify-request")
    public ResponseEntity<ApiResponse<Void>> requestEmailVerification(@RequestParam String email) {
        emailVerificationService.sendVerificationEmail(email);
        return ResponseEntity.ok(ApiResponse.success(null, "이메일 인증 메일이 발송되었습니다."));
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam String token) {
        boolean result = emailVerificationService.verifyEmail(token);
        if (result) {
            return ResponseEntity.ok(ApiResponse.success("이메일 인증이 완료되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.fail("유효하지 않거나 만료된 인증 링크입니다."));
        }
    }
}
