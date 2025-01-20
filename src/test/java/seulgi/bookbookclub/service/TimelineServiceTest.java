package seulgi.bookbookclub.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import seulgi.bookbookclub.domain.Book;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.domain.Timeline;
import seulgi.bookbookclub.repository.BookRepository;
import seulgi.bookbookclub.repository.FollowRepository;
import seulgi.bookbookclub.repository.MemberRepository;
import seulgi.bookbookclub.repository.TimelineRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TimelineServiceTest {

    @Autowired TimelineService timelineService;
    @Autowired TimelineRepository timelineRepository;
    @Autowired  MemberRepository memberRepository;
    @Autowired BookRepository bookRepository;
    @Autowired FollowRepository followRepository;
    @Autowired FollowService followService;

    @Test
    void 글작성(){
        // given
        Member member = new Member("testid", "1234", "닉네임");
        memberRepository.save(member); // Member 저장
        Book book = new Book("1234567890123", "테스트 책", "테스트 저자", "테스트 출판사");
        bookRepository.save(book); // Book 저장
        String contents = "이 책 정말 재미있었습니다!";

        // when
        Integer timelineId = timelineService.createTimeline(member.getMemberSeq(), book.getBookSeq(), contents);

        // then
        Timeline savedTimeline = timelineRepository.findById(timelineId);
        assertNotNull(savedTimeline);
        assertEquals(contents, savedTimeline.getContents());
        assertEquals(member.getMemberSeq(), savedTimeline.getMember().getMemberSeq());
        assertEquals(book.getBookSeq(), savedTimeline.getBook().getBookSeq());

    }

    @Test
    void 글삭제(){
        //given
        Member member = new Member("testid", "1234", "닉네임");
        memberRepository.save(member);
        Book book = new Book("1234567890123", "테스트 책", "테스트 저자", "테스트 출판사");
        bookRepository.save(book);
        String contents = "이 책 정말 재미있었습니다!";
        Integer timelineId = timelineService.createTimeline(member.getMemberSeq(), book.getBookSeq(), contents);

        //when
        timelineService.deleteTimeline(timelineId);

        //then
        assertThrows(Exception.class, () -> {
            timelineRepository.findById(timelineId);
        });
    }

    @Test
    void 특정회원_타임라인조회() {
        // given
        Member member = new Member("testid", "1234", "닉네임");
        memberRepository.save(member);

        Book book1 = new Book("1234567890123", "책1", "저자1", "출판사1");
        Book book2 = new Book("1234567890456", "책2", "저자2", "출판사2");
        bookRepository.save(book1);
        bookRepository.save(book2);

        timelineService.createTimeline(member.getMemberSeq(), book1.getBookSeq(), "첫 번째 글");
        timelineService.createTimeline(member.getMemberSeq(), book2.getBookSeq(), "두 번째 글");

        // when
        List<Timeline> timelines = timelineService.getMemberTimelines(member.getMemberSeq());

        // then
        assertNotNull(timelines);
        assertEquals(2, timelines.size());
        assertEquals("두 번째 글", timelines.get(0).getContents());
        assertEquals("첫 번째 글", timelines.get(1).getContents());
    }

    @Test
    void 팔로우회원_타임라인조회() {
        // given
        Member follower = new Member("followerId", "1234", "팔로워");
        Member following1 = new Member("following1Id", "5678", "팔로잉1");
        Member following2 = new Member("following2Id", "91011", "팔로잉2");

        memberRepository.save(follower);
        memberRepository.save(following1);
        memberRepository.save(following2);

        followService.follow(follower, following1);
        followService.follow(follower, following2);

        Book book1 = new Book("1234567890123", "책1", "저자1", "출판사1");
        Book book2 = new Book("1234567890456", "책2", "저자2", "출판사2");
        bookRepository.save(book1);
        bookRepository.save(book2);

        timelineService.createTimeline(following1.getMemberSeq(), book1.getBookSeq(), "팔로잉1의 첫 번째 글");
        timelineService.createTimeline(following2.getMemberSeq(), book2.getBookSeq(), "팔로잉2의 첫 번째 글");

        // when
        List<Timeline> timelines = timelineService.getFollowedTimelines(follower.getMemberSeq());

        // then
        assertNotNull(timelines);
        assertEquals(2, timelines.size());
        assertEquals("팔로잉1의 첫 번째 글", timelines.get(0).getContents());
        assertEquals("팔로잉2의 첫 번째 글", timelines.get(1).getContents());
    }
}