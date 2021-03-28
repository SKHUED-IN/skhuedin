package com.skhuedin.skhuedin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user")
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_user")
    private User writerUser;

    @Builder
    public Question(Long id, String title, String content, User targetUser, User writerUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.targetUser = targetUser;
        this.writerUser = writerUser;
    }


    private String title;
    private String content;

}
