package com.skhuedin.skhuedin.social.google;

import lombok.Data;

@Data
public class GoogleOAuthResponse {

    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private String idToken;
}