package ddururi.bookbookclub.domain.user.controller;

import ddururi.bookbookclub.domain.user.dto.*;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.domain.user.service.ProfileImageService;
import ddururi.bookbookclub.domain.user.service.UserService;
import ddururi.bookbookclub.global.common.ApiResponse;
import ddururi.bookbookclub.global.jwt.JwtBlacklistService;
import ddururi.bookbookclub.global.jwt.JwtRefreshTokenService;
import ddururi.bookbookclub.global.jwt.JwtUtil;
import ddururi.bookbookclub.global.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static ddururi.bookbookclub.domain.user.policy.UserPolicy.*;

import java.util.concurrent.TimeUnit;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ProfileImageService profileImageService;
    private final JwtRefreshTokenService refreshTokenService;
    private final JwtBlacklistService blacklistService;
    private final JwtUtil jwtUtil;


    //회원가입
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> signup(@Valid @RequestBody UserSignupRequest request) {
        UserResponse response = userService.signup(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    //이메일 중복 확인
    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailDuplicate(@RequestParam String email) {
        boolean isDuplicate = userService.isEmailDuplicate(email);
        return ResponseEntity.ok(ApiResponse.success(isDuplicate));
    }

    //닉네임 중복 확인
    @GetMapping("/check-nickname")
    public ResponseEntity<ApiResponse<Boolean>> checkNicknameDuplicate(@RequestParam String nickname) {
        boolean isDuplicate = userService.isNicknameDuplicate(nickname);
        return ResponseEntity.ok(ApiResponse.success(isDuplicate));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid UserLoginRequest request) {
        UserResponse user = userService.login(request);
        String accessToken = jwtUtil.createToken(user.getEmail());
        String refreshToken = jwtUtil.createRefreshToken();

        refreshTokenService.save(user.getId(), refreshToken, REFRESH_EXPIRATION_DAYS, TimeUnit.DAYS);

        LoginResponse response = new LoginResponse(accessToken, refreshToken, user);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(ApiResponse.success(UserResponse.from(userDetails.getUser())));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<Void>> updateMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        userService.updateProfile(userDetails.getUser().getId(), request);
        return ResponseEntity.ok(ApiResponse.success(null, "회원 정보가 수정되었습니다."));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(@RequestBody RefreshTokenRequest request) {
        if (!refreshTokenService.isValid(request.getUserId(), request.getRefreshToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.fail("Refresh Token이 유효하지 않습니다"));
        }

        User user = userService.findById(request.getUserId());

        String newAccessToken = jwtUtil.createToken(user.getEmail());
        String newRefreshToken = jwtUtil.createRefreshToken();

        refreshTokenService.save(user.getId(), newRefreshToken, REFRESH_EXPIRATION_DAYS, TimeUnit.DAYS);

        return ResponseEntity.ok(ApiResponse.success(
                new LoginResponse(newAccessToken, newRefreshToken, UserResponse.from(user))
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    HttpServletRequest request) {
        refreshTokenService.delete(userDetails.getId());

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String accessToken = header.substring(7);
            long remainingTime = jwtUtil.getRemainingExpiration(accessToken);
            blacklistService.blacklist(accessToken, remainingTime);
        }

        return ResponseEntity.ok(ApiResponse.success(null));
    }
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> withdraw(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.withdraw(userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponse.success(null, "회원 탈퇴가 완료되었습니다."));
    }

    @PostMapping("/{userId}/profile-image")
    public ApiResponse<String> uploadProfileImage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestParam("file") MultipartFile file) {
        Long userId = userDetails.getUser().getId();
        String imageUrl = profileImageService.store(file, userId);
        userService.updateProfileImage(userId, imageUrl);
        return ApiResponse.success(imageUrl);
    }
}
