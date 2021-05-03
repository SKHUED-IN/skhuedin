package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.social.kakao.KakaoOauth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KakaoOauthTest {

    @Autowired
    private KakaoOauth kakaoOauth;

    @Test
    @DisplayName("kakao 사용자 인증 토큰을 발급 받기위한 URL 발급 테스트")
    void getOauthRedirectURL() {

        // given
        String RedirectURL = kakaoOauth.getOauthRedirectURL();

        //when& then
        assertThat(RedirectURL).isEqualTo
                ("https://kauth.kakao.com/oauth/authorize?response_type=code&redirect_uri=http://localhost:8080/auth/kakao/callback&client_id=f4f3a16b864f2046d38669b4f8a2a482");
    }
}
