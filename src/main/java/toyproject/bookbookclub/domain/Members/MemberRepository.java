package toyproject.bookbookclub.domain.Members;

import org.apache.el.stream.Stream;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {
// @Repository 란?
// 멀티쓰레드 환경에서는 hashMap 사용 하면 안됨. ConcurrentHashMap 써야 함.

    private static final Map<String, Member> store = new ConcurrentHashMap<>(); //static 사용
//    private static final Map<String, Member> store = new HashMap<>(); //static 사용

    public Member save(Member member){
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(String id){
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(String memberId, Member updateParam){
        Member findMember = findById(memberId);
        findMember.setPassword(updateParam.getPassword());
        findMember.setNickName(updateParam.getNickName());
    }



    public void clearStore(){
        store.clear();
    }


}
