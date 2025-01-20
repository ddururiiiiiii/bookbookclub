package seulgi.bookbookclub.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import seulgi.bookbookclub.domain.Follow;
import seulgi.bookbookclub.domain.Timeline;

import java.util.List;

@Repository
public class FollowRepository {

    @PersistenceContext
    private EntityManager em;

    //팔로우 추가
    public void save(Follow follow){
        em.persist(follow);
    }

    //팔로우 삭제 (언팔로우)
    public void delete(Follow follow){
        em.remove(follow);
    }

    // memberSeqs를 팔로잉한 회원 목록 조회
    public List<Follow> findFollowerByMember(Integer memberSeq){
        return em.createQuery("SELECT f FROM Follow f WHERE f.following.memberSeq = :memberSeq", Follow.class)
                .setParameter("memberSeq", memberSeq)
                .getResultList();
    }

    // memberSeqs가 팔로잉한 회원 목록 조회
    public List<Follow> findFollowingsByMember(Integer memberSeq) {
        return em.createQuery("SELECT f FROM Follow f WHERE f.follower.memberSeq = :memberSeq", Follow.class)
                .setParameter("memberSeq", memberSeq)
                .getResultList();
    }

    // 팔로우 존재 여부 확인
    public Follow findFollow(Integer followerSeq, Integer followingSeq) {
        return em.createQuery("SELECT f FROM Follow f WHERE f.follower.memberSeq = :followerSeq AND f.following.memberSeq = :followingSeq", Follow.class)
                .setParameter("followerSeq", followerSeq)
                .setParameter("followingSeq", followingSeq)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
