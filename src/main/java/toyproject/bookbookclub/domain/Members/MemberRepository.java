package toyproject.bookbookclub.domain.Members;

import org.apache.el.stream.Stream;
import org.springframework.stereotype.Repository;
import toyproject.bookbookclub.domain.UploadFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {

    private static final Map<String, Member> store = new ConcurrentHashMap<>();


    public Member save(Member member){
        member.setFirstJoinDate(LocalDateTime.now());
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

    public void update(String memberId, UpdateForm updateParam, UploadFile uploadFile){
        Member findMember = findById(memberId);
        findMember.setPassword(updateParam.getPassword());
        findMember.setNickName(updateParam.getNickName());
        findMember.setBios(updateParam.getBios());
        findMember.setProfileImage(uploadFile);
        findMember.setLastUpdateDate(LocalDateTime.now());
    }



    public void clearStore(){
        store.clear();
    }


}
