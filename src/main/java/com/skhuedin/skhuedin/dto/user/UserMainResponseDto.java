package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserMainResponseDto {

    private Long id;
    private String name;

    public UserMainResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }
}
