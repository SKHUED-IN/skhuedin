package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CheckTokenWithCommonResponse;

import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.infra.LoginRequest;
import com.skhuedin.skhuedin.infra.TokenResponse;
import com.skhuedin.skhuedin.service.UserService;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class OauthController {

    private final OauthService oauthService;
    private final UserService userService;

    @PostMapping("/{socialLoginType}/callback")
    public ResponseEntity<? extends BasicResponse> callback(
            @PathVariable("socialLoginType") String socialLoginType,
            @RequestBody TokenResponse response) {

        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(response.getAccessToken());

        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", oAuthToken.getAccessToken());
        // 소셜 로그인을 통해서 사용자의 값을 반환받
        UserSaveRequestDto user = oauthService.requestAccessToken(socialLoginType, oAuthToken);
        String token = Strings.EMPTY;
        Boolean isFirstVisit = false;
        // 사용자가 현재 회원인지 아닌지 확인 작업. 회원이 아니면 회원 가입을 시키고
        if (userService.findByEmail(user.getEmail()) == null) {
            userService.signUp(user);
            isFirstVisit = true;
        } else if (userService.findByEmail(user.getEmail()).getEntranceYear() == null) {
            isFirstVisit = true;
        }
        //회원이면 로그인을 시킴
        token = userService.signIn(user);
        log.info(token);

        UserMainResponseDto responseDto = new UserMainResponseDto(userService.findByEmail(user.getEmail()));
        return ResponseEntity.status(HttpStatus.OK).body((new CheckTokenWithCommonResponse<>(responseDto, token, isFirstVisit)));
    }

    @PostMapping("/{socialLoginType}/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(
            @PathVariable("socialLoginType") String socialLoginType,
            @RequestBody TokenResponse response) {

        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(response.getAccessToken());

        oauthService.logout(socialLoginType, oAuthToken);
    }
}