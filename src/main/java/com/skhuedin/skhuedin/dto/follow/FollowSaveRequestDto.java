package com.skhuedin.skhuedin.dto.follow;


import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowSaveRequestDto {
    private Long id;
    private User toUser;
    private User fromUser;

    @Builder
    public FollowSaveRequestDto(Long id, User toUser, User fromUser) {
        this.id = id;
        this.toUser = toUser;
        this.fromUser = fromUser;
    }

    public Follow toEntity(Follow follow) {
        return Follow.builder()
                .toUser(follow.getToUser())
                .fromUser(follow.getFromUser())
                .build();
    }
}
