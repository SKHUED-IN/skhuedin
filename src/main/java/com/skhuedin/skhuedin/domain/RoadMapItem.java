package com.skhuedin.skhuedin.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class RoadMapItem {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;

    private String content;
    private LocalDate yearMonth;
    private LocalDateTime create;
    private LocalDateTime updated;


    @Builder
    public RoadMapItem(Long id, String content, LocalDate yearMonth,
                       LocalDateTime create, LocalDateTime updated) {
        this.id = id;
        this.content = content;
        this.yearMonth = yearMonth;
        this.create = create;
        this.updated = updated;
    }


}
