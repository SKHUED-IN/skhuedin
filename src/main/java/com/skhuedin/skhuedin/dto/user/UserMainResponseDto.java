package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserMainResponseDto {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Provider provider;
    private String userImageUrl;
    LocalDateTime entranceYear;
    LocalDateTime graduationYear;

    public UserMainResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.provider = user.getProvider();
        this.userImageUrl = user.getUserImageUrl();
        this.entranceYear = user.getEntranceYear();
        this.graduationYear = user.getGraduationYear();
    }
}