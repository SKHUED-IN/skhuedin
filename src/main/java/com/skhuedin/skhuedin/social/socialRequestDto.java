package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;
import lombok.Getter;

@Getter
public class socialRequestDto {

    private Provider socialLoginType;
    private String code;

    public socialRequestDto(socialRequestDto requestDto) {
        this.socialLoginType = requestDto.getSocialLoginType();
        this.code = requestDto.getCode();
    }
}
