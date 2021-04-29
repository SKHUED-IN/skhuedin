package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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

    public User toEntity() {
        User user = User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .provider(this.provider)
                .userImageUrl(this.userImageUrl)
                .entranceYear(this.entranceYear)
                .graduationYear(this.graduationYear)
                .build();
        return user;
    }
}