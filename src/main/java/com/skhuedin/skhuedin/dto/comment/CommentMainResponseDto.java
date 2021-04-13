package com.skhuedin.skhuedin.dto.comment;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentMainResponseDto {

    private Long id;
    private Long questionId;
    private UserMainResponseDto writerUser;
    private String content;
    private CommentMainResponseDto parent;
    private List<CommentMainResponseDto> children;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public CommentMainResponseDto(Comment comment) {
        this.id = comment.getId();
        this.questionId = comment.getQuestion().getId();
        this.writerUser = new UserMainResponseDto(comment.getWriterUser());
        this.content = comment.getContent();
        if (comment.getParent() != null) {
            this.parent = new CommentMainResponseDto(comment.getParent());
        }
        this.children = comment.getChildren()
                .stream()
                .map(child -> new CommentMainResponseDto(child))
                .collect(Collectors.toList());
        this.createdDate = comment.getCreatedDate();
        this.lastModifiedDate = comment.getLastModifiedDate();
    }
}