package com.skhuedin.skhuedin.social.kakao;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.social.SocialOauth;
import org.springframework.stereotype.Component;

@Component
public class KakaoOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }

    @Override
    public String requestAccessToken(String code) {
        return "";
    }

    @Override
    public Provider type() {
        return null;
    }
}