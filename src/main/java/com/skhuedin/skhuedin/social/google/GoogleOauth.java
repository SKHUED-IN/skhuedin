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

        return toGoogleUserSaveRequestDto(profile);
    }

    @Override
    public void logout(OAuthToken oauthToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken.getAccessToken());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                logout + oauthToken.getAccessToken(),
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );
    }

    public UserSaveRequestDto toGoogleUserSaveRequestDto(GoogleProfile googleProfile) {
        UUID password = UUID.randomUUID(); // 임시 비밀번호

        UserSaveRequestDto user = UserSaveRequestDto.builder()
                .email(googleProfile.getEmail())
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