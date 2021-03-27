package com.skhuedin.skhuedin.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class QuestionCategory {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;


    private LocalDateTime create;
    private LocalDateTime updated;


    @Builder
    public QuestionCategory(Long id, String name,
                            LocalDateTime create, LocalDateTime updated) {
        this.id = id;
        this.name = name;
        this.create = create;
        this.updated = updated;
    }

}
