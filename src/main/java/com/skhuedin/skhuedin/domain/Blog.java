package com.skhuedin.skhuedin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"posts"})
public class Blog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private File profile;

    private String content;

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
    private List<Posts> posts = new ArrayList<>();

    @Builder
    public Blog(User user, File profile, String content, List<Posts> posts) {
        this.user = user;
        this.user.addBlog(this);
        this.profile = profile;
        this.content = content;
        if (posts != null) {
            this.posts = posts;
        }
    }

    public void updateBlog(Blog blog) {
        this.user = blog.user;
        this.profile = blog.profile;
        this.content = blog.content;
        this.posts = blog.posts;
    }

    public void addPosts(Posts posts) {
        this.posts.add(posts);
        posts.addBlog(this);
    }
}
