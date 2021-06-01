package com.skhuedin.skhuedin.dto.blog;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.File;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class BlogSaveRequestDto {

    @NotNull(message = "user id는 null이 될 수 없습니다.")
    private Long userId;

    @Size(max = 1000, message = "content의 size는 1000을 넘을 수 없습니다.")
    private String content;

    @Builder
    public BlogSaveRequestDto(Long userId, Long fileId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public Blog toEntity(User user) {
        return Blog.builder()
                .user(user)
                .content(this.content)
                .build();
    }

    public Blog toEntity(User user, File file) {
        return Blog.builder()
                .user(user)
                .profile(file)
                .content(this.content)
                .build();
    }
}