package com.skhuedin.skhuedin.domain;

import com.skhuedin.skhuedin.infra.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String userImageUrl;

    private String entranceYear;

    private String graduationYear;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Blog blog;

    @Builder
    public User(String email, String password, String name, Provider provider,
                String userImageUrl, String entranceYear, String graduationYear, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.userImageUrl = userImageUrl;
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
        this.role = role;
    }

    public void update(User user) {
        this.email = user.email;
        this.password = user.password;
        this.name = user.name;
        this.provider = user.provider;
        this.userImageUrl = user.userImageUrl;
        this.entranceYear = user.entranceYear;
        this.graduationYear = user.graduationYear;
    }

    public void addYear(String entranceYear, String graduationYear) {
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void addBlog(Blog blog) {
        this.blog = blog;
    }
}