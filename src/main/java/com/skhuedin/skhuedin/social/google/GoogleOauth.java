package com.skhuedin.skhuedin.social.google;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.social.SocialOauth;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GoogleOauth implements SocialOauth {

    private String GOOGLE_SNS_BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private String GOOGLE_SNS_CLIENT_ID = "26388048524-72oe5ceuu1n8b51204ub9bmhochpp7gg.apps.googleusercontent.com";
    private String GOOGLE_SNS_CALLBACK_URL = "http://localhost:8080/auth/google/callback";
    private String GOOGLE_SNS_CLIENT_SECRET = "uLKmQf04pnRl3tfHsRxZpPYU";
    private final String GOOGLE_SNS_TOKEN_BASE_URL = "https://oauth2.googleapis.com/token";


    @Override
    public UserSaveRequestDto requestAccessToken(OAuthToken oAuthToken) {

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        UserSaveRequestDto user = null;

        //JSON 파싱을 위한 기본값 세팅
        //요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //ID Token만 추출 (사용자의 정보는 jwt로 인코딩 되어있다)
        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo").queryParam("id_token", oAuthToken.getAccessToken()).toUriString();
        String resultJson = restTemplate.getForObject(requestUrl, String.class);
        GoogleInnerProfile profile = null;
        try {
            profile = mapper.readValue(resultJson, new TypeReference<GoogleInnerProfile>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 사용자 유저로 저장.
        user = saveGoogleUser(profile);


        return user;
    }

    public UserSaveRequestDto saveGoogleUser(GoogleInnerProfile googleProfile) {
        UUID password = UUID.randomUUID(); // 임시 비밀번호

        UserSaveRequestDto user = UserSaveRequestDto.builder()
                .email(googleProfile.getSub())// google 에서 이메일을 주지 않아 든 Google 계정에서 고유하며 재사용되지 않는 사용자의 식별자를 저장
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