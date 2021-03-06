package com.skhuedin.skhuedin.dto.question;

import com.skhuedin.skhuedin.domain.question.Question;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class QuestionMainResponseDto {

    private Long id;
    private UserMainResponseDto targetUser;
    private UserMainResponseDto writerUser;
    private String title;
    private String content;
    private Boolean status;
    private Boolean fix;
    private Integer view;
    private List<CommentMainResponseDto> comments = new ArrayList<>();
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public QuestionMainResponseDto(Question question) {
        this.id = question.getId();
        this.targetUser = new UserMainResponseDto(question.getTargetUser());
        this.writerUser = new UserMainResponseDto(question.getWriterUser());
        this.title = question.getTitle();
        this.content = question.getContent();
        this.status = question.getStatus();
        this.fix = question.getFix();
        this.view = question.getView();
        this.createdDate = question.getCreatedDate();
        this.lastModifiedDate = question.getLastModifiedDate();
    }

    public QuestionMainResponseDto(Question question, List<CommentMainResponseDto> comments) {
        this.id = question.getId();
        this.targetUser = new UserMainResponseDto(question.getTargetUser());
        this.writerUser = new UserMainResponseDto(question.getWriterUser());
        this.title = question.getTitle();
        this.content = question.getContent();
        this.status = question.getStatus();
        this.fix = question.getFix();
        this.view = question.getView();
        this.comments = comments;
        this.createdDate = question.getCreatedDate();
        this.lastModifiedDate = question.getLastModifiedDate();
    }
}