package com.skhuedin.skhuedin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String userImageUrl;

    LocalDateTime entranceYear;

    LocalDateTime graduationYear;

    @Builder
    public User(String email, String password, String name, Provider provider,
                String userImageUrl, LocalDateTime entranceYear, LocalDateTime graduationYear) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.userImageUrl = userImageUrl;
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
    }

    public void updateUser(User user) {
        this.email = user.email;
        this.password = user.password;
        this.name = user.name;
        this.provider = user.provider;
        this.userImageUrl = user.userImageUrl;
        this.entranceYear = user.entranceYear;
        this.graduationYear = user.graduationYear;
    }
}