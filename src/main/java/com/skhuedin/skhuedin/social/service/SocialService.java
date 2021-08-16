package com.skhuedin.skhuedin.social.service;

import com.skhuedin.skhuedin.social.dto.UserInfo;

public interface SocialService {

    String GRANT_TYPE = "Bearer";
    String HEADER_AUTHORIZATION = "Authorization";
    String GOOGLE_USERINFO_URL = "https://openidconnect.googleapis.com/v1/userinfo";
    String KAKAO_USERINFO_URL = "https://kapi.kakao.com/v2/user/me";

    UserInfo getUserInfo(String accessToken);
}