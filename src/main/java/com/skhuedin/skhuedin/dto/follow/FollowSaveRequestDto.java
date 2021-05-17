package com.skhuedin.skhuedin.dto.follow;

import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowSaveRequestDto {

    private Long toUserId;
    private Long fromUserId;

    @Builder
    public FollowSaveRequestDto(Long toUserId, Long fromUserId) {
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }

    public Follow toEntity(User toUSer, User fromUser) {
        return Follow.builder()
                .toUser(toUSer)
                .fromUser(fromUser)
                .build();
    }
}
