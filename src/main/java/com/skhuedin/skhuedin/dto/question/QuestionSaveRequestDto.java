package com.skhuedin.skhuedin.dto.question;

import com.skhuedin.skhuedin.domain.Question;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class QuestionSaveRequestDto {

    private String title;
    private String content;
    private Long targetUserId;
    private User targetUser;
    private Long writerUserId;
    private User writerUser;
    private Boolean status;
    private Boolean fix;

    @Builder
    public QuestionSaveRequestDto(String title, String content, Boolean status, Boolean fix) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.fix = fix;
    }

    public Question toEntity() {
        return Question.builder()
                .title(this.title)
                .content(this.content)
                .targetUser(this.targetUser)
                .writerUser(this.writerUser)
                .status(this.status)
                .fix(this.fix)
                .build();
    }
}