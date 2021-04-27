package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.social.naver.NaverOauth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NaverOauthTest {

    @Autowired
    private NaverOauth naverOauth;

    @Test
    @DisplayName("naver 사용자 인증 토큰을 발급 받기위한 URL 발급 테스트")
    void getOauthRedirectURL() {

        // given
        String RedirectURL = naverOauth.getOauthRedirectURL();

        //when& then
        assertThat(RedirectURL).isEqualTo
                ("https://nid.naver.com/oauth2.0/authorize?state==state&response_type=code&redirect_uri=http://localhost:8080/auth/naver/callback&client_id=xncCqLDs5xAMfdEgui3A");
    }

}
