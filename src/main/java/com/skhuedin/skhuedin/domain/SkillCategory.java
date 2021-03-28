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
public class SkillCategory extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talent_id")
    private Talent talent;


    @Builder
    public SkillCategory( String name) {
        Assert.hasText(name, "이름 값은 필수입니다. ");
        this.name = name;
    }


}
