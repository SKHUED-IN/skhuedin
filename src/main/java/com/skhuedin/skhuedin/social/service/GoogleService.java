package com.skhuedin.skhuedin.social.service;

import com.skhuedin.skhuedin.social.dto.google.GoogleUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleService {

    public static final String GOOGLE_USERINFO_URL = "https://openidconnect.googleapis.com/v1/userinfo";
    public static final String HEADER_NAME = "Authorization";

    private final RestTemplate restTemplate;

    public GoogleUserInfo getUserInfo(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_NAME, accessToken);

        HttpEntity requestEntity = new HttpEntity(headers);
        GoogleUserInfo googleUserInfo = restTemplate.exchange(
                GOOGLE_USERINFO_URL,
                HttpMethod.GET,
                requestEntity,
                GoogleUserInfo.class
        ).getBody();

        return googleUserInfo;
    }
}
