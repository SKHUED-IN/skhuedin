package com.skhuedin.skhuedin.social.naver;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.social.SocialOauth;
import org.springframework.stereotype.Component;

@Component
public class NaverOauth implements SocialOauth {
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