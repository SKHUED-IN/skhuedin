package com.skhuedin.skhuedin.dto.blog;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.dto.file.FileMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
public class BlogMainResponseDto {

    private final Long id;
    private final UserMainResponseDto user;
    private final FileMainResponseDto profile;
    private final String content;
    private Page<PostsMainResponseDto> posts;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    public BlogMainResponseDto(Blog blog) {
        this.id = blog.getId();
        this.user = new UserMainResponseDto(blog.getUser());
        this.profile = new FileMainResponseDto(blog.getProfile());
        this.content = blog.getContent();
        this.createdDate = blog.getCreatedDate();
        this.lastModifiedDate = blog.getLastModifiedDate();
    }

    public BlogMainResponseDto(Blog blog, Page<PostsMainResponseDto> posts) {
        this.id = blog.getId();
        this.user = new UserMainResponseDto(blog.getUser());
        this.profile = blog.getProfile() != null ? new FileMainResponseDto(blog.getProfile()) : null;
        this.content = blog.getContent();
        this.posts = posts;
        this.createdDate = blog.getCreatedDate();
        this.lastModifiedDate = blog.getLastModifiedDate();
    }
}