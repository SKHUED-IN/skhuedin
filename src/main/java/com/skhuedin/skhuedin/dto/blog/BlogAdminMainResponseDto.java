package com.skhuedin.skhuedin.dto.blog;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class BlogAdminMainResponseDto {

    private final Long id;
    private final UserMainResponseDto user;
    private final String createdDate;
    private final String lastModifiedDate;

    public BlogAdminMainResponseDto(Blog blog) {
        this.id = blog.getId();
        this.user = new UserMainResponseDto(blog.getUser());
        this.createdDate = blog.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.lastModifiedDate = blog.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}