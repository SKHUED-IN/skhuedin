package com.skhuedin.skhuedin.dto.question;

import com.skhuedin.skhuedin.domain.question.Question;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class QuestionAdminMainResponseDto {

    private final Long id;
    private final UserMainResponseDto writerUser;
    private final UserMainResponseDto targetUser;
    private final String title;
    private final Boolean status;
    private final Boolean fix;
    private final String createdDate;
    private final String lastModifiedDate;

    public QuestionAdminMainResponseDto(Question question) {
        this.id = question.getId();
        this.writerUser = new UserMainResponseDto(question.getWriterUser());
        this.targetUser = new UserMainResponseDto(question.getTargetUser());
        this.title = question.getTitle();
        this.status = question.getStatus();
        this.fix = question.getFix();
        this.createdDate = question.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.lastModifiedDate = question.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}