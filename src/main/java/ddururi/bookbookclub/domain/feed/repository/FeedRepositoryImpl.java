package ddururi.bookbookclub.domain.feed.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ddururi.bookbookclub.domain.feed.entity.QFeed.feed;
import static ddururi.bookbookclub.domain.like.entity.QLike.like;

@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Feed> findByIdInWithLikeCount(List<Long> ids) {
        return queryFactory
                .selectFrom(feed)
                .leftJoin(like).on(like.feed.id.eq(feed.id))
                .where(feed.id.in(ids), feed.isBlinded.isFalse())
                .groupBy(feed.id)
                .fetch();
    }
}
