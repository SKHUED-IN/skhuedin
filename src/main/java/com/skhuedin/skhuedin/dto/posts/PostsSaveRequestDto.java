package com.skhuedin.skhuedin.dto.posts;

import com.skhuedin.skhuedin.domain.blog.Blog;
import com.skhuedin.skhuedin.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    @NotNull(message = "blog의 id는 null이 될 수 없습니다.")
    private Long blogId;

    @NotEmpty(message = "title이 비어 있습니다.")
    @Size(max = 30, message = "title의 길이는 30을 넘을 수 없습니다.")
    private String title;

    @Size(max = 50000, message = "content의 길이는 50000을 넘을 수 없습니다.")
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