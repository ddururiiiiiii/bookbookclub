package seulgi.bookbookclub.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import seulgi.bookbookclub.domain.Book;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.domain.Timeline;
import seulgi.bookbookclub.repository.BookRepository;
import seulgi.bookbookclub.repository.MemberRepository;
import seulgi.bookbookclub.repository.TimelineRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LikesServiceTest {

    @Autowired private LikesService likesService;

    @Autowired private MemberRepository memberRepository;

    @Autowired private TimelineRepository timelineRepository;

    @Autowired private BookRepository bookRepository;

    @Test
    void 좋아요추가() {
        // given
        Member member = new Member("testMember", "1234", "닉네임","정보1");
        memberRepository.save(member);

        Book book = new Book("12345678910", "책제목", "글쓴이", "출판사");
        bookRepository.save(book);

        Timeline timeline = new Timeline(member, book, "테스트 글 내용");
        timelineRepository.save(timeline);

        // when
        likesService.addLike(member.getMemberSeq(), timeline.getTimelineSeq());

        // then
        Long likeCount = likesService.countLikes(timeline.getTimelineSeq());
        assertEquals(1, likeCount);
    }

    @Test
    void 좋아요중복방지() {
        // given
        Member member = new Member("testMember", "1234", "닉네임","정보1");
        memberRepository.save(member);

        Book book = new Book("12345678910", "책제목", "글쓴이", "출판사");
        bookRepository.save(book);

        Timeline timeline = new Timeline(member, book, "테스트 글 내용");
        timelineRepository.save(timeline);

        likesService.addLike(member.getMemberSeq(), timeline.getTimelineSeq());

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            likesService.addLike(member.getMemberSeq(), timeline.getTimelineSeq());
        });
        assertEquals("이미 좋아요를 눌렀습니다.", exception.getMessage());
    }

    @Test
    void 좋아요취소() {
        // given
        Member member = new Member("testMember", "1234", "닉네임","정보1");
        memberRepository.save(member);

        Book book = new Book("12345678910", "책제목", "글쓴이", "출판사");
        bookRepository.save(book);

        Timeline timeline = new Timeline(member, book, "테스트 글 내용");
        timelineRepository.save(timeline);

        likesService.addLike(member.getMemberSeq(), timeline.getTimelineSeq());

        // when
        likesService.removeLike(member.getMemberSeq(), timeline.getTimelineSeq());

        // then
        Long likeCount = likesService.countLikes(timeline.getTimelineSeq());
        assertEquals(0, likeCount);
    }
}