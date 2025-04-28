package ddururi.bookbookclub.domain.feed.dto;

import ddururi.bookbookclub.domain.feed.entity.Feed;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedResponse {

    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public FeedResponse(Feed feed) {
        this.id = feed.getId();
        this.content = feed.getContent();
        this.createdAt = feed.getCreatedAt();
        this.updatedAt = feed.getUpdatedAt();
    }
}
