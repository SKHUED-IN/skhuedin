package com.skhuedin.skhuedin.social.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.social.SocialOauth;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {

    private final RestTemplate restTemplate;

    @Value("${social.kakao.login}")
    private String login;

    @Value("${social.kakao.logout}")
    private String logout;

    public UserSaveRequestDto requestAccessToken(OAuthToken oauthToken) {
        UserSaveRequestDto user = null;

        // 프로필 받아오기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken.getAccessToken());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers);

        // Http 요청하기 - post 방식으로ㅡ 그리고 응답받음.
        ResponseEntity<String> response2 = restTemplate.exchange(
                login,
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile
                kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //유저 형식에 맞게 저장하기
        user = saveKakaoUser(kakaoProfile);

        return user;
    }

    /*
     * 로그아웃 구현
     */
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
                logout,
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
    }

    /**
     * 카카오에서 받은 프로필로
     * User 정보를 채운 후, 디비에 저장
     */
    public UserSaveRequestDto saveKakaoUser(KakaoProfile kakaoProfile) {
        UUID password = UUID.randomUUID(); // 임시 비밀번호

        UserSaveRequestDto user = UserSaveRequestDto.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .name(kakaoProfile.getKakao_account().getProfile().getNickname())
                .provider(Provider.KAKAO)
                .userImageUrl(kakaoProfile.getProperties().getProfile_image())
                .password(password.toString())
                .entranceYear(null)
                .graduationYear(null)
                .build();
        return user;
    }
}