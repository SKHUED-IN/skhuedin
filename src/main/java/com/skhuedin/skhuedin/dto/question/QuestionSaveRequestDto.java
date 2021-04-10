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
    private User targetUser;
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
                .title(title)
                .content(content)
                .targetUser(targetUser)
                .writerUser(writerUser)
                .status(status)
                .fix(fix)
                .build();
    }
}