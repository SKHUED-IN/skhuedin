package com.skhuedin.skhuedin.dto.follow;

import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class FollowSaveRequestDto {

    @NotNull(message = "toUser의 id는 null이 될 수 없습니다.")
    private Long toUserId;

    @NotNull(message = "fromUser의 id는 null이 될 수 없습니다.")
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
