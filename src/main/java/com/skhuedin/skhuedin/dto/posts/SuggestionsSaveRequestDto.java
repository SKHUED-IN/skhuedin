package com.skhuedin.skhuedin.dto.posts;

import com.skhuedin.skhuedin.domain.blog.Blog;
import com.skhuedin.skhuedin.domain.category.Category;
import com.skhuedin.skhuedin.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class SuggestionsSaveRequestDto {

    @NotEmpty(message = "title이 비어 있습니다.")
    @Size(max = 30, message = "title의 길이는 30을 넘을 수 없습니다.")
    private final String title;

    @Size(max = 5000, message = "content의 길이는 5000을 넘을 수 없습니다.")
    private final String content;

    @Builder
    public SuggestionsSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Posts toEntity(Blog blog, Category category) {
        return Posts.builder()
                .blog(blog)
                .category(category)
                .title(this.title)
                .content(this.content)
                .build();
    }
}