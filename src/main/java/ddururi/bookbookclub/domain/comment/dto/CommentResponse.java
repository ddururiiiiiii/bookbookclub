package ddururi.bookbookclub.domain.comment.dto;

import ddururi.bookbookclub.domain.comment.entity.Comment;
import lombok.Getter;

/**
 * 댓글 응답 DTO
 */
@Getter
public class CommentResponse {

    private final Long id;
    private final String content;
    private final String nickname;
    private final Long userId;
    private final String createdAt;

    private CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
        this.userId = comment.getUser().getId();
        this.createdAt = comment.getCreatedAt().toString();
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment);
    }
}
