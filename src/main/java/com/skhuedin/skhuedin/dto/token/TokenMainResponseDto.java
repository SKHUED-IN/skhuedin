package com.skhuedin.skhuedin.dto.token;

import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.Getter;

@Getter
public class TokenMainResponseDto {

    private final UserMainResponseDto user;
    private final String token;
    private final boolean isFirstVisit;

    public TokenMainResponseDto(UserMainResponseDto user, String token, boolean isFirstVisit) {
        this.user = user;
        this.token = token;
        this.isFirstVisit = isFirstVisit;
    }
}