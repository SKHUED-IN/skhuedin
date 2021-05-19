package com.skhuedin.skhuedin.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posts_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private String title;

    private String content;

    private Integer view;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Posts(Blog blog, String title, String content, Category category) {
        this.blog = blog;
        this.title = title;
        this.content = content;
        this.view = 0;
        this.category = category;
    }

    public void updatePost(Posts post) {
        this.blog = post.blog;
        this.title = post.title;
        this.content = post.content;
        this.view = post.view;
        this.category = post.category;
    }

    public void updateCategory(Category category){
        this.category =category;
    }
    public void updateContent(){
        this.content ="관리자에 의해 삭제되었습니다.";
    }

    public void addView() {
        this.view++;
    }

    public void addBlog(Blog blog) {
        this.blog = blog;
    }
}