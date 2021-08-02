package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSaveOrUpdateRequestDto {

    private String email;
    private String name;
    private Provider provider;
    private String userImageUrl;
    private String entranceYear;
    private String graduationYear;
    private Role role;

    @Builder
    public UserSaveOrUpdateRequestDto(String email, String name, Provider provider, String userImageUrl,
                                      String entranceYear, String graduationYear, Role role) {
        this.email = email;
        this.name = name;
        this.provider = provider;
        this.userImageUrl = userImageUrl;
        this.entranceYear = entranceYear;
        this.graduationYear = graduationYear;
        this.role = role;
    }
}