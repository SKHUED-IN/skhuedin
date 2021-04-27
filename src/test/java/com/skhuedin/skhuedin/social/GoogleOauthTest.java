package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.social.google.GoogleOauth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GoogleOauthTest {

    @Autowired
    private com.skhuedin.skhuedin.social.google.GoogleOauth GoogleOauth;

    @Test
    @DisplayName("google에 사용자 인증 토큰을 발급 받기위한 URL 발급 테스트")
    void getOauthRedirectURL(){

        // given
        String RedirectURL = GoogleOauth.getOauthRedirectURL();

        //when& then
        assertThat(RedirectURL).isEqualTo
                ("https://accounts.google.com/o/oauth2/v2/auth?scope=profile&response_" +
                        "type=code&redirect_uri=http://localhost:8080/auth/google/callback&client_" +
                        "id=26388048524-72oe5ceuu1n8b51204ub9bmhochpp7gg.apps.googleusercontent.com");
    }
}

