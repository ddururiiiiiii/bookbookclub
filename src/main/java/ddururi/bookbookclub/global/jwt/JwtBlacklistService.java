package ddururi.bookbookclub.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private final String PREFIX = "blacklist:access:";

    // 블랙리스트에 등록
    public void blacklist(String token, long expirationMillis) {
        redisTemplate.opsForValue().set(PREFIX + token, "logout", expirationMillis, TimeUnit.MILLISECONDS);
    }

    // 블랙리스트에 있는지 확인
    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey(PREFIX + token);
    }
}
