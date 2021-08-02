package com.skhuedin.skhuedin.dto.user;

import com.skhuedin.skhuedin.domain.user.Role;
import lombok.Getter;

@Getter
public class UserAdminUpdateRequestDto {

    private Long id;
    private Role role;
}
