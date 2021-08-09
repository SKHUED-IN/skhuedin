package com.skhuedin.skhuedin.dto.question;

import com.skhuedin.skhuedin.domain.question.Question;
import com.skhuedin.skhuedin.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class QuestionSaveRequestDto {

    @NotNull(message = "targetUser의 id는 null이 될 수 없습니다.")
    private Long targetUserId;

    @NotNull(message = "writerUser의 id는 null이 될 수 없습니다.")
    private Long writerUserId;

    @NotEmpty(message = "title이 비어 있습니다.")
    @Size(max = 15, message = "title의 길이는 15를 넘을 수 없습니다.")
    private String title;

    @Size(max = 2000, message = "cotent의 길이는 2000을 넘을 수 없습니다.")
    private String content;

    @NotNull(message = "status는 null이 될 수 없습니다.")
    private Boolean status;

    @NotNull(message = "fix는 null이 될 수 없습니다.")
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