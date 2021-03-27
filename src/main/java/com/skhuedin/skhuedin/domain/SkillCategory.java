package com.skhuedin.skhuedin.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class SkillCategory {
    @Id
    @GeneratedValue
    private Long id;
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talent_id")
    private Talent talent;


    private LocalDateTime create;
    private LocalDateTime updated;


    @Builder
    public SkillCategory(Long id, String name, LocalDateTime create, LocalDateTime updated) {
        this.id = id;
        this.name = name;
        this.create = create;
        this.updated = updated;
    }


}
