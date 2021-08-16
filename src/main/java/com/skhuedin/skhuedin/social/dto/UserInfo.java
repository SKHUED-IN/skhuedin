package com.skhuedin.skhuedin.social.dto;

import com.skhuedin.skhuedin.domain.user.User;

public interface UserInfo {

    User toEntity();
    String getEmail();
}