package com.skhuedin.skhuedin.social.dto.google;

import com.skhuedin.skhuedin.domain.user.Provider;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.social.dto.UserInfo;
import lombok.Data;

@Data
public class GoogleUserInfo implements UserInfo {

    private String sub;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String email;
    private boolean email_verified;
    private String locale;

    @Override
    public User toEntity() {
        return User.builder()
                .email(this.email)
                .name(this.name)
                .provider(Provider.GOOGLE)
                .userImageUrl(this.picture)
                .role(Role.ROLE_USER)
                .build();
    }

    @Override
    public String getEmail() {
        return this.email;
    }
}