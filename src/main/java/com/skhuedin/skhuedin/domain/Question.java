package com.skhuedin.skhuedin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user")
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_user")
    private User writerUser;

    @Builder
    public Question(String title, String content, User targetUser, User writerUser) {
        Assert.hasText(title, "제목 값은 필수입니다. ");
        Assert.hasText(content, "내 값은 필수입니다. ");


        this.title = title;
        this.content = content;
        this.targetUser = targetUser;
        this.writerUser = writerUser;
    }


    private String title;
    private String content;

}
