package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;

import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.social.google.GoogleOauth;
import com.skhuedin.skhuedin.social.kakao.KakaoOauth;
import com.skhuedin.skhuedin.social.naver.NaverOauth;

public interface SocialOauth {

    /**
     * 각 Social Login 페이지로 Redirect 처리할 URL Build
     * 사용자로부터 로그인 요청을 받아 Social Login Server 인증용 code 요청
     */
    String getOauthRedirectURL();

    /**
     * API Server로부터 받은 code를 활용하여 사용자 인증 정보 요청
     *
     * @param code API Server 에서 받아온 code
     * @return API 서버로 부터 응답받은 Json 형태의 결과를 string으로 반
     */
    UserSaveRequestDto requestAccessToken(String code);

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