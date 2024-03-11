package toyproject.bookbookclub.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.bookbookclub.domain.Members.Member;
import toyproject.bookbookclub.domain.Members.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    /**
     * @return null이면 로그인 실패 */
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
