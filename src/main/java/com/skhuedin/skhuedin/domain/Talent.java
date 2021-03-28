package com.skhuedin.skhuedin.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Talent extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "talent")
    List<SkillCategory> skillCategories = new ArrayList<>();


    @OneToMany(mappedBy = "talent")
    List<Roadmap> roadmaps = new ArrayList<>();


    private String talentImageUrl;
    private String content;
    private String view;


    private LocalDateTime entranceYear;
    private LocalDateTime graduationYear;

    private LocalDateTime start_career;


    @Builder
    public Talent(String talentImageUrl, String content,
                  String view, LocalDateTime entranceYear, LocalDateTime graduationYear,
                  LocalDateTime start_career) {

        Assert.hasText(talentImageUrl, "talentImageUrl 값은 필수입니다. ");
        Assert.hasText(content, "content 값은 필수입니다. ");
        Assert.hasText(view, "view 값은 필수입니다. ");
        Assert.hasText(String.valueOf(entranceYear), "entranceYear 값은 필수입니다. ");
        Assert.hasText(String.valueOf(graduationYear), "graduationYear 값은 필수입니다. ");
        Assert.hasText(String.valueOf(start_career), "start_career 값은 필수입니다. ");


        this.talentImageUrl = talentImageUrl;
        this.content = content;
        this.view = view;
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
        this.start_career = start_career;
    }


}
