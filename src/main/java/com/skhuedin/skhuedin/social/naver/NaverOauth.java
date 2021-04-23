package com.skhuedin.skhuedin.social.naver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.service.UserService;
import com.skhuedin.skhuedin.social.SocialOauth;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class NaverOauth implements SocialOauth {

    private final UserService userService;

    private String NAVER_SNS_BASE_URL = "https://nid.naver.com/oauth2.0/authorize";
    private String NAVER_SNS_CLIENT_ID = "xncCqLDs5xAMfdEgui3A";
    private String NAVER_SNS_CALLBACK_URL = "http://localhost:8080/auth/naver/callback";
    private String NAVER_SNS_CLIENT_SECRET = "G8wydgDb7C";
    private final String NAVER_SNS_TOKEN_BASE_URL = "https://nid.naver.com/oauth2.0/token";

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", NAVER_SNS_CLIENT_ID);
        params.put("response_type", "code");
        params.put("redirect_uri", NAVER_SNS_CALLBACK_URL);
        params.put("state=", "state");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return NAVER_SNS_BASE_URL + "?" + parameterString;
    }

    @Override
    public String requestAccessToken(String code) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("client_id", NAVER_SNS_CLIENT_ID);
        params.add("client_secret", NAVER_SNS_CLIENT_SECRET);
        params.add("grant_type", "authorization_code");
        params.add("state", "state");
        params.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> naverTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - post 방식으로ㅡ 그리고 응답받음.
        ResponseEntity<String> response = rt.exchange(
                NAVER_SNS_TOKEN_BASE_URL,
                HttpMethod.POST,
                naverTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 프로필 받아오기
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> naverProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - post 방식으로ㅡ 그리고 응답받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                naverProfileRequest2,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        NaverProfile naverProfile = null;
        try {
            naverProfile = objectMapper2.readValue(response2.getBody(), NaverProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        userService.signUp(saveNaverUser(naverProfile));
        return response2.getBody();
    }

    /**
     * 네이버에서 받은 프로필로
     * User 정보를 채운 후, 디비에 저장
     */
    public User saveNaverUser(NaverProfile naverProfile) {
        UUID password = UUID.randomUUID(); // 임시 비밀번호

        User user = User.builder()
                .email(naverProfile.getResponse().getEmail())
                .name(naverProfile.getResponse().getName())
                .provider(Provider.NAVER)
                .userImageUrl(naverProfile.getResponse().getProfile_image())
                .password(password.toString())
                .token(null)
                .entranceYear(null)
                .graduationYear(null)
                .build();
        userService.save(user);
        return user;
    }
}