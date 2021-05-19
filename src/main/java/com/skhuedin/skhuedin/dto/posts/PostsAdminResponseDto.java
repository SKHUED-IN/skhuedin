package com.skhuedin.skhuedin.dto.posts;

import com.skhuedin.skhuedin.domain.Posts;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsAdminResponseDto {

    private Long id;
    private Long blogId;
    private String name;
    private String title;
    private String content;
    private Integer view;
    private Long category;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public PostsAdminResponseDto(Posts posts) {
        this.id = posts.getId();
        this.blogId = posts.getBlog().getId();
        this.name = posts.getBlog().getUser().getName();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.view = posts.getView();
        this.category =posts.getCategory().getId();
        this.createdDate = posts.getCreatedDate();
        this.lastModifiedDate = posts.getLastModifiedDate();
    }
}