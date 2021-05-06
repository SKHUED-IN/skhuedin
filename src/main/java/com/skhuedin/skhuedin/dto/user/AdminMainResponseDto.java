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
public class AdminMainResponseDto {

    private String email;
    private Role role;

    @Builder
    AdminMainResponseDto(User user) {
        this.email = user.getEmail();
        this.role = user.getRole();

    }

}