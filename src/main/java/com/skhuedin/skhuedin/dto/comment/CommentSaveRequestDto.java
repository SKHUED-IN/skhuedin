package com.skhuedin.skhuedin.dto.comment;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    @NotNull(message = "question의 id는 null이 될 수 없습니다.")
    private Long questionId;

    @NotNull(message = "writer user의 id는 null이 될 수 없습니다.")
    private Long writerUserId;

    @NotNull
    @Size(max = 1000, message = "content의 size는 1000을 넘을 수 없습니다.")
    private String content;

    @Builder
    public CommentSaveRequestDto(Long questionId, Long writerUserId, String content) {
        this.questionId = questionId;
        this.writerUserId = writerUserId;
        this.content = content;
    }

    public Comment toEntity(Question question, User writerUser) {
        return Comment.builder()
                .question(question)
                .writerUser(writerUser)
                .content(this.content)
                .build();
    }
}