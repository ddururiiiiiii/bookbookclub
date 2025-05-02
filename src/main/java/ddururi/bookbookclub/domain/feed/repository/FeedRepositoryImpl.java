package ddururi.bookbookclub.domain.feed.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ddururi.bookbookclub.domain.feed.entity.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import static ddururi.bookbookclub.domain.feed.entity.QFeed.feed;

/**
 * 피드 QueryDSL 커스텀 레포지토리 구현체
 */
@Repository
@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Feed> searchFeeds(String keyword, Pageable pageable) {
        // 블라인드 제외 + content, title, author, publisher 검색 + 최신순 정렬
        return queryFactory
                .selectFrom(feed)
                .leftJoin(feed.book).fetchJoin()
                .where(
                        feed.isBlinded.isFalse()
                                .and(
                                        feed.content.containsIgnoreCase(keyword)
                                                .or(feed.book.title.containsIgnoreCase(keyword))
                                                .or(feed.book.author.containsIgnoreCase(keyword))
                                                .or(feed.book.publisher.containsIgnoreCase(keyword))
                                )
                )
                .orderBy(feed.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Feed> findFeedsByUserId(Long userId, Pageable pageable) {
        return queryFactory.selectFrom(feed)
                .where(feed.user.id.eq(userId).and(feed.isBlinded.isFalse()))
                .orderBy(feed.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
