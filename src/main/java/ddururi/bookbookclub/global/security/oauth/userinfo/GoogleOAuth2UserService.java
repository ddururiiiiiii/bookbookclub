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

@RequiredArgsConstructor
@Service
public class GoogleOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String providerId = oAuth2User.getAttribute("sub"); // 구글 ID
        AuthProvider provider = AuthProvider.GOOGLE;

        return userRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseGet(() -> {
                    // 닉네임 중복 검사
                    if (userRepository.existsByNickname(name)) {
                        throw new OAuth2AuthenticationException("이미 사용 중인 닉네임입니다.");
                    }

                    User newUser = userRepository.save(User.createSocialUser(email, name, provider, providerId));
                    return new CustomUserDetails(newUser);
                });
    }
}
