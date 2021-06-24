package com.skhuedin.skhuedin.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuedin.skhuedin.social.google.GoogleOauth;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql("/truncate.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {"classpath:/application-real-oauth.yml"})
class OauthControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GoogleOauth googleOauth;

    @Autowired
    RestTemplate rt;

//    @Value("${social.google.baseFullUrl}")
    private String baseFullUrl;

    @DisplayName("google로그인_시도하면_OAuth인증창_등장한다")
    @Test
    @Disabled
    public void google_login_oauth_page_ture() throws Exception {
        //given 어떤 값이 주어지고
        ResponseEntity<String> responseEntity = rt.getForEntity(baseFullUrl, String.class);

        //when& then
        assertEquals(responseEntity.getStatusCode().toString(), "200 OK");
    }
}