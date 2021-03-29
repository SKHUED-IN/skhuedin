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
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoadmapItem extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "roadmap_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;

    private String content;

    private LocalDate yearMonth;

    @Builder
    public RoadmapItem(String content, LocalDate yearMonth) {

        Assert.hasText(content, "내용 은 필수입니다. ");
        Assert.hasText(String.valueOf(yearMonth), "yearMonth 필수입니다. ");
        this.content = content;
        this.yearMonth = yearMonth;
    }
}