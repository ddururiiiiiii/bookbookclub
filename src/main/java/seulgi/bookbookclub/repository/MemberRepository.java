package seulgi.bookbookclub.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import seulgi.bookbookclub.domain.Member;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;


    public void save(Member member){
        em.persist(member);
    }

    public void update(Member member) {
        em.merge(member);
    }

    public Member findByMemberSeq(Integer memberSeq){
        return em.find(Member.class, memberSeq);
    }

    public List<Member> findByMemberId(String memberId) {
        return em.createQuery("SELECT m FROM Member m WHERE m.memberId = :memberId", Member.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }


    public List<Member> finaAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }


}
