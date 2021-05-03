package com.skhuedin.skhuedin.dto.follow;


import com.skhuedin.skhuedin.domain.Follow;
import com.skhuedin.skhuedin.domain.User;

import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FollowMainResponseDto {
    private Long id;
    private UserMainResponseDto toUser;
    private UserMainResponseDto fromUser;
    private List<UserMainResponseDto> users = new ArrayList<>();

    public FollowMainResponseDto(Follow follow) {
        this.id = follow.getId();
        this.toUser = new UserMainResponseDto(follow.getToUser());
        this.fromUser = new UserMainResponseDto(follow.getFromUser());
    }

    public FollowMainResponseDto(Follow follow, List<UserMainResponseDto> users) {
        this.id = follow.getId();
        this.toUser = new UserMainResponseDto(follow.getToUser());
        this.fromUser = new UserMainResponseDto(follow.getFromUser());
        this.users = users;
    }
}
