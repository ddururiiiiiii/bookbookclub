package ddururi.bookbookclub.domain.like.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static ddururi.bookbookclub.domain.like.entity.QLike.like;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findFeedIdsByUserId(Long userId) {
        return queryFactory.select(like.feed.id)
                .from(like)
                .where(like.user.id.eq(userId))
                .fetch();
    }
}
