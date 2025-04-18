package ddururi.bookbookclub.domain.user.service;

import ddururi.bookbookclub.domain.user.dto.*;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 회원가입_성공() {
        // given
        UserSignupRequest request = new UserSignupRequest("test@example.com", "pw1234", "닉네임");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPW");

        User savedUser = User.create(request.getEmail(), "hasedPw", request.getNickname());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // when
        UserResponse response = userService.signup(request);

        // then
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getNickname()).isEqualTo("닉네임");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 로그인_실패_비밀번호불일치() {
        // given
        String email = "user@email.com";
        String rawPassword = "wrongPW";

        User user = User.create(email, "hashedPassword", "닉네임");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, "hashedPassword")).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> userService.login(new UserLoginRequest(email, rawPassword)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 회원정보수정_성공() {
        // given
        Long userId = 1L;
        User user = User.create("user@email.com", "hashedPassword", "이전닉네임");
        user.setBio("이전 소개");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserUpdateRequest request = new UserUpdateRequest("새닉네임", "새 소개");

        // when
        userService.updateProfile(userId, request);

        // then
        assertThat(user.getNickname()).isEqualTo("새닉네임");
        assertThat(user.getBio()).isEqualTo("새 소개");
    }
}