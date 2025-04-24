package ddururi.bookbookclub.global.security.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddururi.bookbookclub.global.common.ApiResponse;
import ddururi.bookbookclub.global.jwt.JwtRefreshTokenService;
import ddururi.bookbookclub.global.common.TokenResponse;
import ddururi.bookbookclub.global.jwt.JwtUtil;
import ddururi.bookbookclub.global.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import static ddururi.bookbookclub.domain.user.policy.UserPolicy.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final JwtRefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // JWT 발급
        String accessToken = jwtUtil.createToken(userDetails.getEmail());
        String refreshToken = jwtUtil.createRefreshToken();

        // Redis 저장
        refreshTokenService.save(userDetails.getId(), refreshToken, REFRESH_EXPIRATION_DAYS, TimeUnit.DAYS);

        // 응답
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
                ApiResponse.success(new TokenResponse(accessToken, refreshToken))
        ));
    }
}
