package com.skhuedin.skhuedin.social.kakao;

import com.skhuedin.skhuedin.social.SocialOauth;
import org.springframework.stereotype.Component;

@Component
public class KakaoOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }
}