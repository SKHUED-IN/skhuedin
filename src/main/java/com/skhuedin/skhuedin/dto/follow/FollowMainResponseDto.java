package com.skhuedin.skhuedin.dto.follow;

import com.skhuedin.skhuedin.domain.Blog;
import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowMainResponseDto {
    private Long id;
    private User user;

    @Builder
    public FollowMainResponseDto(Follow follow) {
        this.id = follow.getId();
        this.user = follow.getUser();
    }

    public Follow toEntity(Follow follow) {
        return Follow.builder()
                .user(this.user)
                .build();
    }
}
