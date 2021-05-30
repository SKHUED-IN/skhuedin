package com.skhuedin.skhuedin.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoResponse {
    private String kakaoId;

    public KakaoResponse(String kakaoId) {
        this.kakaoId = kakaoId;
    }
}
