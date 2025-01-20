package seulgi.bookbookclub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seulgi.bookbookclub.domain.Likes;
import seulgi.bookbookclub.domain.Member;
import seulgi.bookbookclub.domain.Timeline;
import seulgi.bookbookclub.repository.LikesRepository;
import seulgi.bookbookclub.repository.MemberRepository;
import seulgi.bookbookclub.repository.TimelineRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final TimelineRepository timelineRepository;

    // 좋아요 추가
    @Transactional
    public void addLike(Integer memberSeq, Integer timelineSeq) {
        Member member = memberRepository.findByMemberSeq(memberSeq);
        Timeline timeline = timelineRepository.findById(timelineSeq);

        likesRepository.findLike(memberSeq, timelineSeq).ifPresent(like -> {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        });

        Likes like = new Likes(member, timeline);
        likesRepository.save(like);
    }

    // 좋아요 취소
    @Transactional
    public void removeLike(Integer memberSeq, Integer timelineSeq) {
        Likes like = likesRepository.findLike(memberSeq, timelineSeq)
                .orElseThrow(() -> new IllegalArgumentException("좋아요가 존재하지 않습니다."));
        likesRepository.delete(like);
    }

    // 특정 타임라인의 좋아요 개수 조회
    public Long countLikes(Integer timelineSeq) {
        return likesRepository.countLikesByTimeline(timelineSeq);
    }

}
