package com.skhuedin.skhuedin.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionCategory extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;



    @Builder
    public QuestionCategory(Long id, String name) {
        this.id = id;
        this.name = name;

    }

}
