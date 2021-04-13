package com.skhuedin.skhuedin.social.naver;

import com.skhuedin.skhuedin.social.SocialOauth;
import org.springframework.stereotype.Component;

@Component
public class NaverOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }
}