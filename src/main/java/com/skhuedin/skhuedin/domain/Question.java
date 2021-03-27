package com.skhuedin.skhuedin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetUser")
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writerUser")
    private User writerUser;

    @Builder
    public Question(Long id, String title, String content,
                    LocalDateTime create, LocalDateTime updated) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.create = create;
        this.updated = updated;
    }


    private String title;
    private String content;

    private LocalDateTime create;
    private LocalDateTime updated;
}
