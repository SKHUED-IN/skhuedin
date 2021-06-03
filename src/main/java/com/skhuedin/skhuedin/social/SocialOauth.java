package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.social.google.GoogleOauth;
import com.skhuedin.skhuedin.social.kakao.KakaoOauth;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import com.skhuedin.skhuedin.social.naver.NaverOauth;

public interface SocialOauth {

    /**
     * API Server로부터 받은 code를 활용하여 사용자 인증 정보 요청
     *
     * @return API 서버로 부터 응답받은 Json 형태의 결과를 string으로 반
     */
    UserSaveRequestDto requestAccessToken(OAuthToken oAuthToken);

    void logout(OAuthToken oAuthToken);

    default Provider type() {
        if (this instanceof GoogleOauth) {
            return Provider.GOOGLE;
        } else if (this instanceof NaverOauth) {
            return Provider.NAVER;
        } else if (this instanceof KakaoOauth) {
            return Provider.KAKAO;
        } else {
            return null;
        }
    }
}