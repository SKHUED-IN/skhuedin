package com.skhuedin.skhuedin.social.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final UserService userService;

    /**
     * 맨 처음 카카오에서, 디벨로퍼 클라이언트 아이디를 보낸 후,
     * 접근 인증을 받기 위한 절차
     */
    public OAuthToken oAuthToken(String code) {
        //HttpHeader 오브젝트 생성
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "f4f3a16b864f2046d38669b4f8a2a482");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // Http 요청하기 - post 방식으로ㅡ 그리고 응답받음.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oauthToken;
    }

    /**
     * 인증 받은 토큰으로 프로필 정보 받기
     *
     * @param oauthToken
     */
    public KakaoProfile getProfile(OAuthToken oauthToken) {
        //HttpHeader 오브젝트 생성
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // Http 요청하기 - post 방식으로ㅡ 그리고 응답받음.
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
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
        return kakaoProfile;
    }

    /**
     * 카카오에서 받은 프로필로
     * User 정보를 채운 후, 디비에 저장
     */
    public User saveKakaoUser(KakaoProfile kakaoProfile) {
        UUID password = UUID.randomUUID(); // 임시 비밀번호

        User user = User.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .name(kakaoProfile.getKakao_account().getProfile().getNickname())
                .provider(Provider.KAKAO)
                .userImageUrl(kakaoProfile.getProperties().getProfile_image())
                .password(password.toString())
                .entranceYear(null)
                .graduationYear(null)
                .build();
        userService.save(user);
        return user;
    }
}
