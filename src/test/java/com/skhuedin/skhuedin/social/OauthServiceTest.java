package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.social.google.GoogleOauth;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql("/truncate.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OauthServiceTest {

    @Autowired
    private List<SocialOauth> socialOauthList;

    @Autowired
    private GoogleOauth googleOauth;

    @DisplayName("google 로 들어온 요청 확인")
    @Test
    @Disabled
    void requestAccessToken() {
        //when 무엇을 했을 때
        SocialOauth socialOauth = findSocialOauthByType("google");

        //then 어떤 값을 원한다.
        assertAll(
                () -> assertEquals(socialOauth, googleOauth)
        );
    }

    private SocialOauth findSocialOauthByType(String socialLoginType) {

        Provider provider = null;

        if (socialLoginType.equals("google")) {
            provider = Provider.GOOGLE;
        } else {
            throw new IllegalArgumentException("알 수 없는 SocialLoginType 입니다.");
        }

        Provider finalProvider = provider;
        return socialOauthList.stream()
                .filter(x -> x.type() == finalProvider)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialOauth 입니다."));
    }
}