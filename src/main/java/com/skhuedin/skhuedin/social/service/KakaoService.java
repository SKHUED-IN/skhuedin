package com.skhuedin.skhuedin.social.service;

import com.skhuedin.skhuedin.social.dto.kakao.KakaoUserInfo;
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
public class KakaoService {

    public static final String KAKAO_USERINFO_URL = "https://kapi.kakao.com/v2/user/me";
    public static final String HEADER_NAME = "Authorization";

    private final RestTemplate restTemplate;

    public KakaoUserInfo getUserInfo(String accessToken) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_NAME, "Bearer " + accessToken);

        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        KakaoUserInfo kakaoUserInfo = restTemplate.exchange(
                KAKAO_USERINFO_URL,
                HttpMethod.GET,
                requestEntity,
                KakaoUserInfo.class
        ).getBody();

        return kakaoUserInfo;
    }
}