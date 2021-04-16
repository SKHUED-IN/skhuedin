package com.skhuedin.skhuedin.social.google;

import com.skhuedin.skhuedin.social.SocialOauth;
import org.springframework.stereotype.Component;

@Component
public class GoogleOauth implements SocialOauth {
    @Override
    public String getOauthRedirectURL() {
        return "";
    }
}