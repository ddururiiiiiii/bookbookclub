package ddururi.bookbookclub.domain.feed.repository;

import ddururi.bookbookclub.domain.feed.entity.Feed;

import java.util.List;

public interface FeedRepositoryCustom {
    List<Feed> findByIdInWithLikeCount(List<Long> ids);
}