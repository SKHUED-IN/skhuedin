package com.skhuedin.skhuedin.dto.comment;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveRequestDto {

    private Long questionId;
    private Question question;
    private Long writerUserId;
    private User writerUser;
    private String content;
    private Long parentCommentId;
    private Comment parentComment;

    @Builder
    public CommentSaveRequestDto(Long questionId, Long writerUserId, String content, Long parentCommentId) {
        this.questionId = questionId;
        this.writerUserId = writerUserId;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    public Comment toEntity() {
        return Comment.builder()
                .question(this.question)
                .writerUser(this.writerUser)
                .content(this.content)
                .build();
    }
}