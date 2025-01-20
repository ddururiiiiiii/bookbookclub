package seulgi.bookbookclub;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import seulgi.bookbookclub.domain.*;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1() {

            //회원 2명 생성
            Member member1 = new Member("이름1", "1234", "닉네임1");
            Member member2 = new Member("이름2", "3456", "닉네임2");
            em.persist(member1);
            em.persist(member2);

            //책 2개 생성
            Book book1 = new Book("111111111111","책이름1", "저자1", "출판사1");
            Book book2 = new Book("222222222222","책이름2", "저자2", "출판사2");
            em.persist(book1);
            em.persist(book2);

            //이름1에 타임라인2개 생성
            Timeline timeline1 = new Timeline(member1, "내용1", book1);
            Timeline timeline2 = new Timeline(member1, "내용2", book2);
            em.persist(timeline1);
            em.persist(timeline2);

            //이름2가 이름1을 팔로잉
            Follow follow = new Follow(member2, member1);
            em.persist(follow);

            //이름2가 이름1의 타임라인을 조항요
            Likes likes = new Likes(member2, timeline1);
            em.persist(likes);
        }
    }
}