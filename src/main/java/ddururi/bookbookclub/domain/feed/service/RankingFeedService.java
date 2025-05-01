package ddururi.bookbookclub.domain.feed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 피드 랭킹 서비스 (좋아요순, 조회수순 관리)
 */
@Service
@RequiredArgsConstructor
public class RankingFeedService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 좋아요 카운트 증가/감소
     *
     * @param period weekly, monthly, yearly, total
     * @param feedId 피드 ID
     * @param increment 증가(+1), 감소(-1)
     */
    public void incrementLike(String period, Long feedId, int increment) {
        String redisKey = getLikeKey(period);
        redisTemplate.opsForZSet().incrementScore(redisKey, feedId.toString(), increment);
    }

    /**
     * 좋아요 TOP N 피드 조회
     */
    public List<Long> getTopLikedFeeds(String period, int topN) {
        String redisKey = getLikeKey(period);
        Set<String> feedIds = redisTemplate.opsForZSet()
                .reverseRange(redisKey, 0, topN - 1);
        return feedIds.stream().map(Long::valueOf).toList();
    }

    /**
     * Redis 키 매핑
     */
    private String getLikeKey(String period) {
        return switch (period.toLowerCase()) {
            case "weekly" -> "popular:feed:like:weekly";
            case "monthly" -> "popular:feed:like:monthly";
            case "yearly" -> "popular:feed:like:yearly";
            case "total" -> "popular:feed:like:total";
            default -> throw new IllegalArgumentException("Invalid period: " + period);
        };
    }
}
