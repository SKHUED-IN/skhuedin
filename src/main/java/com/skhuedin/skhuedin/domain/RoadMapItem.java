package com.skhuedin.skhuedin.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;


import javax.persistence.*;
import java.time.LocalDate;


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
    public RoadMapItem(String content, LocalDate yearMonth) {
        Assert.hasText(content, "내용 은 필수입니다. ");
        Assert.hasText(String.valueOf(yearMonth), "yearMonth 필수입니다. ");

        this.content = content;
        this.yearMonth = yearMonth;

    }


}
