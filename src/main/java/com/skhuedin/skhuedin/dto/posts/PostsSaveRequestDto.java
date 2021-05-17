package com.skhuedin.skhuedin.dto.posts;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private Long blogId;
    private String title;
    private String content;

    @Builder
    public PostsSaveRequestDto(Long blogId, String title, String content) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
    }

    public Posts toEntity(Blog blog) {
        return Posts.builder()
                .blog(blog)
                .title(this.title)
                .content(this.content)
                .build();
    }
}