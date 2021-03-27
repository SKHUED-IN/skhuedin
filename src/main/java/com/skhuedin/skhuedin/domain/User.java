package com.skhuedin.skhuedin.domain;


import jdk.jfr.Enabled;
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
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String userImageUrl;
    private LocalDateTime create;
    private LocalDateTime updated;

    @Builder
    public User(Long id, String email, String password, Provider provider,
                String userImageUrl, LocalDateTime create, LocalDateTime updated) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.userImageUrl = userImageUrl;
        this.create = create;
        this.updated = updated;
    }

    @OneToMany(mappedBy = "user")
    private List<Follow> follows = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Talent> talents = new ArrayList<>();

    @OneToMany(mappedBy = "targetUser")
    private List<Question> targetQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "writerUser")
    private List<Question> writerQuestions = new ArrayList<>();


}
