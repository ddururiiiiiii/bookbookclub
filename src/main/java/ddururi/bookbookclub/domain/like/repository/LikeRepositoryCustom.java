package ddururi.bookbookclub.domain.like.repository;

import java.util.List;

public interface LikeRepositoryCustom {
    List<Long> findFeedIdsByUserId(Long userId);
}