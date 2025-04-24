package ddururi.bookbookclub.global.security.oauth.userinfo;

import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.domain.user.enums.AuthProvider;
import ddururi.bookbookclub.domain.user.repository.UserRepository;
import ddururi.bookbookclub.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class NaverOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 네이버 응답 구조: { "response": { "id": "...", "email": "...", "nickname": "..." } }
        Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");

        String email = (String) response.get("email");
        String nickname = (String) response.get("nickname");  // 또는 name
        String providerId = (String) response.get("id");
        AuthProvider provider = AuthProvider.NAVER;

        return userRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseGet(() -> {
                    if (userRepository.existsByNickname(nickname)) {
                        throw new OAuth2AuthenticationException("이미 사용 중인 닉네임입니다.");
                    }

                    User newUser = userRepository.save(User.createSocialUser(email, nickname, provider, providerId));
                    return new CustomUserDetails(newUser);
                });
    }
}
