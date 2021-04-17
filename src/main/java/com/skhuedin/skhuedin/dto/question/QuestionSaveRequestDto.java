package com.skhuedin.skhuedin.dto.question;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionSaveRequestDto {

    private Long targetUserId;
    private Long writerUserId;
    private String title;
    private String content;
    private Boolean status;
    private Boolean fix;

    @Builder
    public QuestionSaveRequestDto(Long targetUserId, Long writerUserId, String title,
                                  String content, Boolean status, Boolean fix) {
        this.targetUserId = targetUserId;
        this.writerUserId = writerUserId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.fix = fix;
    }

    public Question toEntity(User targetUser, User writerUser) {
        return Question.builder()
                .targetUser(targetUser)
                .writerUser(writerUser)
                .title(this.title)
                .content(this.content)
                .status(this.status)
                .fix(this.fix)
                .build();
    }
}