package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.social.google.GoogleOauth;
import com.skhuedin.skhuedin.social.kakao.KakaoOauth;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import com.skhuedin.skhuedin.social.naver.NaverOauth;

public interface SocialOauth {

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