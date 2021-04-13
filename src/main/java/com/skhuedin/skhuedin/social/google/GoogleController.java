package com.skhuedin.skhuedin.social.google;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Controller
public class GoogleController {

    @GetMapping("google/auth")
    public String googleAuth(Model model, @RequestParam(value = "code") String authCode)
            throws JsonProcessingException {

        //HTTP Request를 위한 RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        //Google OAuth Access Token 요청을 위한 파라미터 세팅
        GoogleProfile googleOAuthRequestParam = GoogleProfile
                .builder()
                .clientId("26388048524-7fk1f9hfcasl0jjt06j7a750lcd1uqqd.apps.googleusercontent.com")
                .clientSecret("i8tUSheF1lO1RJhXa_xfraRY")
                .code(authCode)
                .redirectUri("http://localhost:8080/login/google/auth")
                .grantType("authorization_code").build();

        //JSON 파싱을 위한 기본값 세팅
        //요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //AccessToken 발급 요청
        ResponseEntity<String> resultEntity = restTemplate.postForEntity("http://localhost:8080/login/google/auth", googleOAuthRequestParam, String.class);

        //Token Request
        GoogleAuthToken result = mapper.readValue(resultEntity.getBody(), new TypeReference<GoogleAuthToken>() {
        });

        //ID Token만 추출 (사용자의 정보는 jwt로 인코딩 되어있다)
        String jwtToken = result.getIdToken();
        String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
                .queryParam("id_token", jwtToken).encode().toUriString();

        String resultJson = restTemplate.getForObject(requestUrl, String.class);

        Map<String, String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>() {
        });
        model.addAllAttributes(userInfo);
        model.addAttribute("token", result.getAccessToken());

        return "/googleLogin.html";

    }
}
