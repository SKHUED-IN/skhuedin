package com.skhuedin.skhuedin.dto.follow;

import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

@Getter
public class FollowMainResponseDto {
    private Long id;
    private UserMainResponseDto toUser;
    private UserMainResponseDto fromUser;

    public FollowMainResponseDto(Follow follow) {
        this.id = follow.getId();
        this.toUser = new UserMainResponseDto(follow.getToUser());
        this.fromUser = new UserMainResponseDto(follow.getFromUser());
    }
}
