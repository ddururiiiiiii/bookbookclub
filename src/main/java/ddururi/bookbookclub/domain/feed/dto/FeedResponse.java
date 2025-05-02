package ddururi.bookbookclub.domain.feed.dto;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 피드 응답 DTO
 * - 좋아요 수, 책 정보 포함
 */
@Getter
public class FeedResponse {

    private final Long id;
    private final String content;
    private final long likeCount;
    private final boolean liked;
    private final String bookTitle;
    private final String bookAuthor;
    private final String bookThumbnailUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * 피드 엔티티에서 응답 DTO로 변환
     */
    public FeedResponse(Feed feed, long likeCount, boolean liked) {
        this.id = feed.getId();
        this.content = feed.getContent();
        this.likeCount = likeCount;
        this.liked = liked;
        this.bookTitle = feed.getBook().getTitle();
        this.bookAuthor = feed.getBook().getAuthor();
        this.bookThumbnailUrl = feed.getBook().getThumbnailUrl();
        this.createdAt = feed.getCreatedAt();
        this.updatedAt = feed.getUpdatedAt();
    }
}
