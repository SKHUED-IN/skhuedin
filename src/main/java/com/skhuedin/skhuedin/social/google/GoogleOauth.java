package com.skhuedin.skhuedin.social.google;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.social.SocialOauth;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth {

    private final RestTemplate restTemplate;

    @Value("${social.google.login}")
    private String login;

    @Value("${social.google.logout}")
    private String logout;

    @Override
    public UserSaveRequestDto requestAccessToken(OAuthToken oAuthToken) {

        ObjectMapper mapper = new ObjectMapper();
        UserSaveRequestDto user = null;

        //JSON 파싱을 위한 기본값 세팅
        //요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String reqURL = login + oAuthToken.getAccessToken();
        String resultJson = restTemplate.getForObject(reqURL, String.class);
        GoogleProfile profile = null;

        try {
            profile = mapper.readValue(resultJson, new TypeReference<GoogleProfile>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 사용자 유저로 저장.
        user = saveGoogleUser(profile);

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
                logout + oauthToken.getAccessToken(),
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
    }

    public UserSaveRequestDto saveGoogleUser(GoogleProfile googleProfile) {
        UUID password = UUID.randomUUID(); // 임시 비밀번호

        UserSaveRequestDto user = UserSaveRequestDto.builder()
                .email(googleProfile.getId())// google 에서 이메일을 주지 않아 든 Google 계정에서 고유하며 재사용되지 않는 사용자의 식별자를 저장
                .name(googleProfile.getName())
                .provider(Provider.GOOGLE)
                .userImageUrl(googleProfile.getPicture())
                .password(password.toString())
                .entranceYear(null)
                .graduationYear(null)
                .build();
        return user;
    }
}