package com.skhuedin.skhuedin.dto.comment;

import com.skhuedin.skhuedin.domain.Comment;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentMainResponseDto {

    private Long id;
    private Long questionId;
    private UserMainResponseDto writerUser;
    private String content;
    private Long parentId;
    private List<CommentMainResponseDto> children = new ArrayList<>();
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public CommentMainResponseDto(Comment comment) {
        this.id = comment.getId();
        this.questionId = comment.getQuestion().getId();
        this.writerUser = new UserMainResponseDto(comment.getWriterUser());
        this.content = comment.getContent();
        if (comment.getParent() != null) { // 부모 comment 가 존재하면
            this.parentId = comment.getParent().getId();
        } else { // 부모 comment 가 존재하지 않으면
            this.parentId = null;
        }
        this.createdDate = comment.getCreatedDate();
        this.lastModifiedDate = comment.getLastModifiedDate();
    }

    public CommentMainResponseDto(Comment comment, List<Comment> children) {
        this(comment);
        this.children = children.stream()
                .map(child -> new CommentMainResponseDto(child))
                .collect(Collectors.toList());
    }
}