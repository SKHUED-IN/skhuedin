package com.skhuedin.skhuedin.dto.posts;

import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsMainResponseDto {

    private Long id;
    private Long blogId;
    private UserMainResponseDto user;
    private String title;
    private String content;
    private Integer view;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public PostsMainResponseDto(Posts posts) {
        this.id = posts.getId();
        this.blogId = posts.getBlog().getId();
        this.user = new UserMainResponseDto(posts.getBlog().getUser());
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.view = posts.getView();
        this.createdDate = posts.getCreatedDate();
        this.lastModifiedDate = posts.getLastModifiedDate();
    }
}