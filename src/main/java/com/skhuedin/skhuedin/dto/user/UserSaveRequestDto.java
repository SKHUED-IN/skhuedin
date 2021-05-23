package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;
    private String password;
    private String name;
    private Provider provider;
    private String userImageUrl;
    private String entranceYear;
    private String graduationYear;

    private Role role;


    @Builder
    public UserSaveRequestDto(String email,
                              String password, String name,
                              Provider provider, String userImageUrl,
                              String entranceYear, String graduationYear, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.userImageUrl = userImageUrl;
        this.role = role;
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
    }

    @Builder
    public UserSaveRequestDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.provider = user.getProvider();
        this.userImageUrl = user.getUserImageUrl();
        this.entranceYear = user.getEntranceYear();
        this.graduationYear = user.getGraduationYear();
    }

    public User toEntity() {

        if(this.role ==null){
            this.role = Role.USER;
        }
        User user = User.builder()

                .email(this.email)
                .password(this.password)
                .name(this.name)
                .provider(this.provider)
                .userImageUrl(this.userImageUrl)
                .entranceYear(this.entranceYear)
                .graduationYear(this.graduationYear)
                .role(this.role)
                .build();
        return user;
    }

    public void addYear(String entranceYear, String graduationYear) {
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
    }

    public User toEntity(User targetUser) {
        if(this.role ==null){
            this.role = Role.USER;
        }
        User user = User.builder()
                .email(targetUser.getEmail())
                .password(targetUser.getPassword())
                .name(targetUser.getName())
                .provider(targetUser.getProvider())
                .userImageUrl(targetUser.getUserImageUrl())
                .entranceYear(this.getEntranceYear())
                .graduationYear(this.getGraduationYear())
                .role(this.role)
                .build();
        return user;
    }
}