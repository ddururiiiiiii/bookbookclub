package ddururi.bookbookclub.domain.emailverification.service;

import ddururi.bookbookclub.domain.emailverification.entity.EmailVerification;
import ddururi.bookbookclub.domain.emailverification.repository.EmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JavaMailSender mailSender;
    private final EmailVerificationRepository emailVerificationRepository;

    public void sendVerificationEmail(String email) {
        String token = UUID.randomUUID().toString();
        String redisKey = "email:verify:" + token;

        redisTemplate.opsForValue().set(redisKey, email, 10, TimeUnit.MINUTES);

        String link = "http://localhost:8080/api/email/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[북북클럽] 이메일 인증");
        message.setText("이메일 인증을 위해 아래 링크를 클릭해주세요:\n" + link);

        mailSender.send(message);
    }

    public boolean verifyEmail(String token) {
        String redisKey = "email:verify:" + token;
        String email = redisTemplate.opsForValue().get(redisKey);

        if (email == null) return false;

        redisTemplate.delete(redisKey);

        Optional<EmailVerification> optional = emailVerificationRepository.findByEmail(email);

        EmailVerification verification = optional
                .map(existing -> {
                    existing.markAsVerified();
                    return existing;
                })
                .orElseGet(() -> EmailVerification.builder()
                        .email(email)
                        .verified(true)
                        .verifiedAt(LocalDateTime.now())
                        .build());

        verification.markAsVerified();
        emailVerificationRepository.save(verification);
        return true;
    }

    public boolean isEmailVerified(String email) {
        return emailVerificationRepository.existsByEmailAndVerifiedIsTrue(email);
    }
}
