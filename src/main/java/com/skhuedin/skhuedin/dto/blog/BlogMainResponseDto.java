package com.skhuedin.skhuedin.dto.blog;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.dto.file.FileMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BlogMainResponseDto {

    private Long id;
    private UserMainResponseDto user;
    private FileMainResponseDto profile;
    private String content;
    private List<PostsMainResponseDto> posts = new ArrayList<>();
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public BlogMainResponseDto(Blog blog) {
        this.id = blog.getId();
        this.user = new UserMainResponseDto(blog.getUser());
        this.profile = new FileMainResponseDto(blog.getProfile());
        this.content = blog.getContent();
        this.createdDate = blog.getCreatedDate();
        this.lastModifiedDate = blog.getLastModifiedDate();
    }

    public BlogMainResponseDto(Blog blog, List<PostsMainResponseDto> posts) {
        this.id = blog.getId();
        this.user = new UserMainResponseDto(blog.getUser());
        this.profile = blog.getProfile() != null ? new FileMainResponseDto(blog.getProfile()) : null;
        this.content = blog.getContent();
        this.posts = posts;
        this.createdDate = blog.getCreatedDate();
        this.lastModifiedDate = blog.getLastModifiedDate();
    }
}