package com.skhuedin.skhuedin.dto.blog;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BlogMainResponseDto {

    private Long id;
    private UserMainResponseDto user;
    private String profileImageUrl;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public BlogMainResponseDto(Blog blog) {
        this.id = blog.getId();
        this.user = new UserMainResponseDto(blog.getUser());
        this.profileImageUrl = blog.getProfileImageUrl();
        this.content = blog.getContent();
        this.createdDate = blog.getCreatedDate();
        this.lastModifiedDate = blog.getLastModifiedDate();
    }
}