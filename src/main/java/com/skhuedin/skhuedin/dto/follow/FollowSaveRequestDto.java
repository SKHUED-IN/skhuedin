package com.skhuedin.skhuedin.dto.follow;

import com.skhuedin.skhuedin.domain.follow.Follow;
import com.skhuedin.skhuedin.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class FollowSaveRequestDto {

    @NotNull(message = "fromUser의 id는 null이 될 수 없습니다.")
    private Long fromUserId;


    @NotNull(message = "toUser의 id는 null이 될 수 없습니다.")
    private Long toUserId;

    @Builder
    public FollowSaveRequestDto(Long fromUserId, Long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public Follow toEntity(User fromUser, User toUser) {
        return Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
    }
}
