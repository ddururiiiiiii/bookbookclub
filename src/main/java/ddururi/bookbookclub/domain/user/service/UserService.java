package ddururi.bookbookclub.domain.user.service;


import ddururi.bookbookclub.domain.emailverification.service.EmailVerificationService;
import ddururi.bookbookclub.global.exception.DuplicateNicknameException;
import ddururi.bookbookclub.domain.user.dto.UserLoginRequest;
import ddururi.bookbookclub.domain.user.dto.UserResponse;
import ddururi.bookbookclub.domain.user.dto.UserSignupRequest;
import ddururi.bookbookclub.domain.user.dto.UserUpdateRequest;
import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.domain.user.repository.UserRepository;
import ddururi.bookbookclub.global.exception.DuplicateEmailException;
import ddururi.bookbookclub.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;

    //회원가입
    public UserResponse signup(UserSignupRequest request){
        validateDuplicateEmail(request.getEmail());
        validateDuplicateNickname(request.getNickname());

        // 이메일 인증 확인
        if (!emailVerificationService.isEmailVerified(request.getEmail())) {
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }

        User user = User.create(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname()
        );

        userRepository.save(user);

        return UserResponse.from(user);
    }

    //이메일 중복 확인
    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    //닉네임 중복 확인
    private void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }

    // 이메일 중복 확인 (API 용도)
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    //닉네임 중복 확인 (API 용도)
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    //로그인
    public UserResponse login(UserLoginRequest request){
        User user = validateUserLogin(request.getEmail(), request.getPassword());
        return UserResponse.from(user);
    }

    //비밀번호 확인
    private User validateUserLogin(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    // 회원정보 수정
    @Transactional
    public void updateProfile(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // 닉네임이 바뀌었을 때만 중복 검사를 수행
        if (!user.getNickname().equals(request.getNickname())) {
            validateDuplicateNickname(request.getNickname());
        }

        user.setNickname(request.getNickname());
        user.setBio(request.getBio());
        // save 호출 안 해도 됨 → JPA가 변경감지로 UPDATE 수행
    }
}
