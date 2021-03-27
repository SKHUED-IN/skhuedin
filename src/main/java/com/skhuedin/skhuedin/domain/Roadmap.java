package com.skhuedin.skhuedin.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Roadmap {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talent_id")
    private Talent talent;


    @Builder
    public Roadmap(Long id, LocalDateTime create, LocalDateTime updated) {
        this.id = id;
        this.create = create;
        this.updated = updated;
    }


    @OneToMany(mappedBy = "roadmap")
    List<RoadMapItem> roadMapItems = new ArrayList<>();


    private LocalDateTime create;
    private LocalDateTime updated;

}
