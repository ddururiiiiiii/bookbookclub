package ddururi.bookbookclub.domain.user.scheduler;

import ddururi.bookbookclub.domain.user.entity.User;
import ddururi.bookbookclub.domain.user.enums.UserStatus;
import ddururi.bookbookclub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCleanupScheduler {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 3시
    @Transactional
    public void cleanUpWithdrawnUsers() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        List<User> usersToDelete = userRepository.findAllByStatusAndWithdrawnAtBefore(UserStatus.WITHDRAWN, oneYearAgo);
        userRepository.deleteAll(usersToDelete);
    }
}
