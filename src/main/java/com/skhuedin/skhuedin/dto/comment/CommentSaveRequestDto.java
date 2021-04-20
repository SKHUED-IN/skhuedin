package com.skhuedin.skhuedin.dto.comment;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private Long questionId;
    private Long writerUserId;
    private String content;
    private Long parentId;

    @Builder
    public CommentSaveRequestDto(Long questionId, Long writerUserId, String content, Long parentId) {
        this.questionId = questionId;
        this.writerUserId = writerUserId;
        this.content = content;
        this.parentId = parentId;
    }

    public Comment toEntity(Question question, User writerUser) {
        return Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content(this.content)
                .build();
    }

    public Comment toEntity(Question question, User writerUser, Comment parent) {
        return Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content(this.content)
                .parent(parent)
                .build();
    }
}