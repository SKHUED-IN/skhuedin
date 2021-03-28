package com.skhuedin.skhuedin.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoadMapItem extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;

    private String content;
    private LocalDate yearMonth;


    @Builder
    public RoadMapItem(Long id, String content, LocalDate yearMonth) {
        this.id = id;
        this.content = content;
        this.yearMonth = yearMonth;

    }


}
