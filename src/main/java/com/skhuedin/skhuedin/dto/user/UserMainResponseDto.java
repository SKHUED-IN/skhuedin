package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.User;
import lombok.Getter;

@Getter
public class UserMainResponseDto {

    private Long id;
    private String email;
    private String name;

    public UserMainResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}