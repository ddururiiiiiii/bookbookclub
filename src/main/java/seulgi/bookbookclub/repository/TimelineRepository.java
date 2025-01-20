package seulgi.bookbookclub.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import seulgi.bookbookclub.domain.Follow;
import seulgi.bookbookclub.domain.State;
import seulgi.bookbookclub.domain.Timeline;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TimelineRepository {

    @PersistenceContext
    private EntityManager em;

    //저장
    public void save(Timeline timeline){
        em.persist(timeline);
    }

    //삭제
    public void delete(Timeline timeline) {
        timeline.delete(); //상태변경
    }

    // 특정 회원의 타임라인 조회
    public List<Timeline> findTimelinesByMember(Integer memberSeq) {
        return em.createQuery("SELECT t FROM Timeline t WHERE t.member.memberSeq = :memberSeq AND t.state = :state ORDER BY t.createdDate DESC", Timeline.class)
                .setParameter("memberSeq", memberSeq)
                .setParameter("state", State.ACTIVE)
                .getResultList();
    }

    // 타임라인 ID로 조회
    public Timeline findById(Integer timelineSeq) {
        return em.createQuery("SELECT t FROM Timeline t WHERE t.timelineSeq = :timelineSeq AND t.state = :state", Timeline.class)
                .setParameter("timelineSeq", timelineSeq)
                .setParameter("state", State.ACTIVE)
                .getSingleResult();
    }

    // 팔로우한 회원들의 타임라인 조회
    public List<Timeline> findByFollowers(List<Integer> memberSeqs) {
        return em.createQuery("SELECT t FROM Timeline t WHERE t.member.memberSeq IN :memberSeqs ORDER BY t.createdDate", Timeline.class)
                .setParameter("memberSeqs", memberSeqs)
                .getResultList();
    }

}
