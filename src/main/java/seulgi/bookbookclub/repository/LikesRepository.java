package seulgi.bookbookclub.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import seulgi.bookbookclub.domain.Likes;

import java.util.Optional;

@Repository

public class LikesRepository {
    @PersistenceContext
    private EntityManager em;

    //좋아요 추가
    public void save(Likes likes){
        em.persist(likes);
    }

    //좋아요 해제
    public void delete(Likes likes){
        em.remove(likes);
    }

    // 한 회원이 특정 타임라인에 좋아요 눌렀는지 조회
    public Optional<Likes> findLike(Integer memberSeq, Integer timelineSeq) {
        return em.createQuery("SELECT l FROM Likes l WHERE l.member.memberSeq = :memberSeq AND l.timeline.timelineSeq = :timelineSeq", Likes.class)
                .setParameter("memberSeq", memberSeq)
                .setParameter("timelineSeq", timelineSeq)
                .getResultStream()
                .findFirst();
    }

    // 특정 타임라인의 좋아요 개수 조회
    public Long countLikesByTimeline(Integer timelineSeq) {
        return em.createQuery("SELECT COUNT(l) FROM Likes l WHERE l.timeline.timelineSeq = :timelineSeq", Long.class)
                .setParameter("timelineSeq", timelineSeq)
                .getSingleResult();
    }
}
