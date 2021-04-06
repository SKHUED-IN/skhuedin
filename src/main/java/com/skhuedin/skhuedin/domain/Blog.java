package com.skhuedin.skhuedin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Blog extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "blog_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String profileImageUrl;

    private String content;

    @Builder
    public Blog(User user, String profileImageUrl, String content) {
        this.user = user;
        this.profileImageUrl = profileImageUrl;
        this.content = content;
    }

    public void updateBlog(Blog blog) {
        this.user = blog.user;
        this.profileImageUrl = blog.profileImageUrl;
        this.content = blog.content;
    }
}
