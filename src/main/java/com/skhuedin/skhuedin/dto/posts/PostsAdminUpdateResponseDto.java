package com.skhuedin.skhuedin.dto.posts;

import com.skhuedin.skhuedin.domain.posts.Posts;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class PostsAdminUpdateResponseDto {

    private final Long id;
    private final BlogMainResponseDto blog;
    private final String title;
    private final String content;
    private final Boolean deleteStatus;
    private final CategoryMainResponseDto category;
    private final String createDate;
    private final String lastModifiedDate;

    public PostsAdminUpdateResponseDto(Posts posts) {
        this.id = posts.getId();
        this.blog = new BlogMainResponseDto(posts.getBlog());
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.deleteStatus = posts.getDeleteStatus();
        this.category = new CategoryMainResponseDto(posts.getCategory());
        this.createDate = posts.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.lastModifiedDate = posts.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}