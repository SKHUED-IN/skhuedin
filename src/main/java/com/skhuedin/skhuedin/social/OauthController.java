package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class OauthController {
    private final OauthService oauthService;
    private final UserService userService;

    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     *
     * @param socialLoginType (GOOGLE, FACEBOOK, NAVER, KAKAO)
     */
    @GetMapping("/{socialLoginType}")
    public void socialLoginType(
            @PathVariable("socialLoginType") Provider socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     *
     * @param socialLoginType (GOOGLE, FACEBOOK, NAVER, KAKAO)
     * @param code            API Server 로부터 넘어노는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 String 문자열 (access_token, refresh_token 등)
     */

    @GetMapping("/{socialLoginType}/callback")
    public ResponseEntity<? extends BasicResponse> callback(
            @PathVariable("socialLoginType") Provider socialLoginType,
            @RequestParam("code") String code) {

        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        User user = oauthService.requestAccessToken(socialLoginType, code);

        // user 인증을 위한 자체 토큰을 발급받아 헤더에 저장,데이터에 user 값도 저장 해서 보냄
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization", "Bearer " + userService.signIn(user));

        return new ResponseEntity<>(new CommonResponse<>(user),header, HttpStatus.OK);
    }
}