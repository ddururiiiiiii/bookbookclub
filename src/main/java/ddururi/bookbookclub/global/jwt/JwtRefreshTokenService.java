package ddururi.bookbookclub.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtRefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;
    private final String PREFIX = "refresh:user:";

    public void save(Long userId, String token, long duration, TimeUnit unit) {
        redisTemplate.opsForValue().set(PREFIX + userId, token, duration, unit);
    }

    public String get(Long userId) {
        return redisTemplate.opsForValue().get(PREFIX + userId);
    }

    public void delete(Long userId) {
        redisTemplate.delete(PREFIX + userId);
    }

    public boolean isValid(Long userId, String token) {

        String saved = get(userId);
        return saved != null
                && saved.equals(token)
                && jwtUtil.validateToken(token);
    }
}

