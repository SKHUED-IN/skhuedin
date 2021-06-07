package com.skhuedin.skhuedin.dto.comment;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentMainResponseDto {

    private final Long id;
    private final Long questionId;
    private final UserMainResponseDto writerUser;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    public CommentMainResponseDto(Comment comment) {
        this.id = comment.getId();
        this.questionId = comment.getQuestion().getId();
        this.writerUser = new UserMainResponseDto(comment.getWriterUser());
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.lastModifiedDate = comment.getLastModifiedDate();
    }
}