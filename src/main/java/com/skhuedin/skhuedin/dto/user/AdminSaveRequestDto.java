package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.Role;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSaveRequestDto {

    private String email;
    private String password;

    @Builder
    public AdminSaveRequestDto(String email,
                               String password) {
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        User user = User.builder()
                .email(this.email)
                .password(this.password)
                .role(Role.ADMIN)
                .build();
        return user;
    }
}
