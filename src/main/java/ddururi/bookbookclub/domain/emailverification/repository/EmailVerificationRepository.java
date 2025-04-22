package ddururi.bookbookclub.domain.emailverification.repository;

import ddururi.bookbookclub.domain.emailverification.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByEmail(String email);
    boolean existsByEmailAndVerifiedIsTrue(String email);
}
