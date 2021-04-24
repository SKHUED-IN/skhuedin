package com.skhuedin.skhuedin.dto.blog;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlogSaveRequestDto {

    private Long userId;
    private String profileImageUrl;
    private String content;

    @Builder
    public BlogSaveRequestDto(Long userId, String profileImageUrl, String content) {
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
        this.content = content;
    }

    public Blog toEntity(User user) {
        return Blog.builder()
                .user(user)
                .profileImageUrl(this.profileImageUrl)
                .content(this.content)
                .build();
    }
}