package com.skhuedin.skhuedin.dto.follow;


import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowSaveRequestDto {
    private Long id;
    private User user;

    @Builder
    public FollowSaveRequestDto(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Follow toEntity(Follow follow) {
        return Follow.builder()
                .user(this.user)
                .build();
    }
}
