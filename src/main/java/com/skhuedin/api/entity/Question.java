package com.skhuedin.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_user_id")
    private User writerUser;

    private String title;

    private String content;

    @Builder
    public Question(User targetUser, User writerUser, String title, String content) {

        Assert.hasText(String.valueOf(targetUser), "targetUser 값은 필수입니다. ");
        Assert.hasText(String.valueOf(writerUser), "writerUser 값은 필수입니다. ");
        Assert.hasText(title, "제목 값은 필수입니다. ");
        Assert.hasText(content, "내용 값은 필수입니다. ");
        this.targetUser = targetUser;
        this.writerUser = writerUser;
        this.title = title;
        this.content = content;
    }
}