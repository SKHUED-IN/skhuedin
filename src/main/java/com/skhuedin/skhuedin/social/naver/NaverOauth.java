package com.skhuedin.skhuedin.social.naver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.social.SocialOauth;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class NaverOauth implements SocialOauth {

    private final RestTemplate restTemplate;
    @Value("${social.naver}")
    private String logoutUrl;

    @Override
    public UserSaveRequestDto requestAccessToken(OAuthToken oAuthToken) {
// 프로필 받아오기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(headers);

        // Http 요청하기 - post 방식으로 ㅡ 그리고 응답받음.
        ResponseEntity<String> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naverProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        NaverProfile naverProfile = null;
        try {
            naverProfile = objectMapper.readValue(response.getBody(), NaverProfile.class);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        UserSaveRequestDto user = saveNaverUser(naverProfile);

        return user;
    }

    @Override
    public void logout(OAuthToken oauthToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken.getAccessToken());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers);

        // Http 요청하기 - post 방식으로ㅡ 그리고 응답받음.
        ResponseEntity<String> response2 = restTemplate.exchange(
                logoutUrl + oauthToken.getAccessToken() + "&service_provider=NAVER",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
    }

    /**
     * 네이버에서 받은 프로필로
     * User 정보를 채운 후, 디비에 저장
     */
    public UserSaveRequestDto saveNaverUser(NaverProfile naverProfile) {
        UUID password = UUID.randomUUID(); // 임시 비밀번호

        return UserSaveRequestDto.builder()
                .email(naverProfile.getResponse().getEmail())
                .name(naverProfile.getResponse().getName())
                .provider(Provider.NAVER)
                .userImageUrl(naverProfile.getResponse().getProfile_image())
                .password(password.toString())
                .entranceYear(null)
                .graduationYear(null)
                .build();
    }
}