package com.skhuedin.skhuedin.domain;


import jdk.jfr.Enabled;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String userImageUrl;


    @Builder
    public User(Long id, String email, String password, Provider provider,
                String userImageUrl) {
        this.id =id;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.userImageUrl = userImageUrl;

    }


}
