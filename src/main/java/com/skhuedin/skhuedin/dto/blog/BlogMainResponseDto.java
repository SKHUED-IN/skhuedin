package com.skhuedin.skhuedin.dto.blog;

import com.skhuedin.skhuedin.domain.blog.Blog;
import com.skhuedin.skhuedin.domain.UploadFile;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
public class BlogMainResponseDto {

    private final Long id;
    private final UserMainResponseDto user;
    private final UploadFile uploadFile;
    private final String content;
    private Page<PostsMainResponseDto> posts;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    public BlogMainResponseDto(Blog blog) {
        this.id = blog.getId();
        this.user = new UserMainResponseDto(blog.getUser());
        this.uploadFile = blog.getUploadFile();
        this.content = blog.getContent();
        this.createdDate = blog.getCreatedDate();
        this.lastModifiedDate = blog.getLastModifiedDate();
    }

    public BlogMainResponseDto(Blog blog, Page<PostsMainResponseDto> posts) {
        this.id = blog.getId();
        this.user = new UserMainResponseDto(blog.getUser());
        this.uploadFile = blog.getUploadFile();
        this.content = blog.getContent();
        this.posts = posts;
        this.createdDate = blog.getCreatedDate();
        this.lastModifiedDate = blog.getLastModifiedDate();
    }
}