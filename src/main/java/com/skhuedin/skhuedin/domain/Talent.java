package com.skhuedin.skhuedin.domain;

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
    public Talent(User user, String talentImageUrl, String content,
                  LocalDateTime entranceYear, LocalDateTime graduationYear, LocalDateTime startCareer) {

        Assert.notNull(user, "user 값은 필수 입니다. ");
        Assert.hasText(talentImageUrl, "talentImageUrl 값은 필수입니다. ");
        Assert.hasText(content, "content 값은 필수입니다. ");
        Assert.notNull(entranceYear, "entranceYear 값은 필수입니다. ");
        Assert.notNull(graduationYear, "graduationYear 값은 필수입니다. ");
        Assert.notNull(startCareer, "start_career 값은 필수입니다. ");

        this.user = user;
        this.talentImageUrl = talentImageUrl;
        this.content = content;
        this.view = 0;
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
        this.startCareer = startCareer;
    }

    public void updateTalent(Talent talent) {
        this.talentImageUrl = talent.talentImageUrl;
        this.content = talent.content;
        this.entranceYear = talent.entranceYear;
        this.graduationYear = talent.graduationYear;
        this.startCareer = talent.startCareer;
    }

    public void addView() {
        ++view;
    }
}