package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;
    private String password;
    private String name;
    private Provider provider;
    private String userImageUrl;
    LocalDate entranceYear;
    LocalDate graduationYear;

    @Builder
    public UserSaveRequestDto(String email,
                              String password, String name,
                              Provider provider, String userImageUrl,
                              LocalDate entranceYear, LocalDate graduationYear) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.userImageUrl = userImageUrl;
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
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
                .role(Role.USER)
                .build();
        return user;
    }

    public User toEntity(User targetUser) {
        User user = User.builder()
                .email(targetUser.getEmail())
                .password(targetUser.getPassword())
                .name(targetUser.getName())
                .provider(targetUser.getProvider())
                .userImageUrl(targetUser.getUserImageUrl())
                .entranceYear(this.getEntranceYear())
                .graduationYear(this.getGraduationYear())
                .role(Role.USER)
                .build();
        return user;
    }

}
