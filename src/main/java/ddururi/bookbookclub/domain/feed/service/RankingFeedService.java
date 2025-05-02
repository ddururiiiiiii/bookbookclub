package ddururi.bookbookclub.domain.feed.service;

import ddururi.bookbookclub.domain.feed.dto.FeedResponse;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import ddururi.bookbookclub.domain.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 피드 랭킹 서비스 (좋아요순, 조회수순 관리)
 */
@Service
@RequiredArgsConstructor
public class RankingFeedService {

    private final RedisTemplate<String, String> redisTemplate;
    private final FeedRepository feedRepository;

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

    public List<FeedResponse> getTopLikedFeedDetails(String period, int topN, Long userId) {
        String redisKey = getLikeKey(period);

        // Redis에서 ID + score(좋아요 수) 꺼내기
        Set<ZSetOperations.TypedTuple<String>> feedTuples = redisTemplate.opsForZSet()
                .reverseRangeWithScores(redisKey, 0, topN - 1);

        if (feedTuples == null || feedTuples.isEmpty()) return List.of();

        List<Long> topFeedIds = new ArrayList<>();
        Map<Long, Long> likeCountMap = new HashMap<>();

        for (ZSetOperations.TypedTuple<String> tuple : feedTuples) {
            Long feedId = Long.valueOf(tuple.getValue());
            Long likeCount = tuple.getScore().longValue();
            topFeedIds.add(feedId);
            likeCountMap.put(feedId, likeCount);
        }

        // DB에서 상세 조회
        List<Feed> feeds = feedRepository.findByIdInAndIsBlindedFalse(topFeedIds);
        Map<Long, Feed> feedMap = feeds.stream()
                .collect(Collectors.toMap(Feed::getId, f -> f));

        // 순서 + 좋아요 수 맞춰서 DTO로 변환
        return topFeedIds.stream()
                .map(feedId -> {
                    Feed feed = feedMap.get(feedId);
                    if (feed == null) return null;
                    Long likeCount = likeCountMap.getOrDefault(feedId, 0L);
                    return new FeedResponse(feed, likeCount, false);
                })
                .filter(Objects::nonNull)
                .toList();
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
