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
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Talent extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "talent_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String talentImageUrl;

    private String content;

    private int view;

    private LocalDateTime entranceYear;

    private LocalDateTime graduationYear;

    private LocalDateTime startCareer;

    @Builder
    public Talent(User user, String talentImageUrl, String content, int view,
                  LocalDateTime entranceYear, LocalDateTime graduationYear, LocalDateTime startCareer) {

        Assert.hasText(String.valueOf(user), "user 값은 필수입니다. ");
        Assert.hasText(talentImageUrl, "talentImageUrl 값은 필수입니다. ");
        Assert.hasText(content, "content 값은 필수입니다. ");
        Assert.hasText(String.valueOf(view), "view 값은 필수입니다. ");
        Assert.hasText(String.valueOf(entranceYear), "entranceYear 값은 필수입니다. ");
        Assert.hasText(String.valueOf(graduationYear), "graduationYear 값은 필수입니다. ");
        Assert.hasText(String.valueOf(startCareer), "start_career 값은 필수입니다. ");
        this.user = user;
        this.talentImageUrl = talentImageUrl;
        this.content = content;
        this.view = view;
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
        this.startCareer = startCareer;
    }
}