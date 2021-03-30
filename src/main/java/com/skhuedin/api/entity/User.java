package com.skhuedin.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String userImageUrl;

    @Builder
    public User(String email, String password, Provider provider, String userImageUrl) {

        Assert.hasText(email, "email 값은 필수입니다. ");
        Assert.hasText(password, "password 값은 필수입니다. ");
        Assert.hasText(provider.toString(), "provider 값은 필수입니다. ");
        Assert.hasText(userImageUrl, "userImageUrl 값은 필수입니다. ");
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.userImageUrl = userImageUrl;
    }
}