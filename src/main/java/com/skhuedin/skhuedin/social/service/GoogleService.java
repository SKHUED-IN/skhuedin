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
public class GoogleService implements SocialService {

    private final RestTemplate restTemplate;

    @Override
    public GoogleUserInfo getUserInfo(String accessToken) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_AUTHORIZATION, GRANT_TYPE + " " + accessToken);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        GoogleUserInfo googleUserInfo = restTemplate.exchange(
                GOOGLE_USERINFO_URL,
                HttpMethod.GET,
                requestEntity,
                GoogleUserInfo.class
        ).getBody();

        return googleUserInfo;
    }
}