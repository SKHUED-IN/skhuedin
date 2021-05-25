package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CheckTokenWithCommonResponse;
import com.skhuedin.skhuedin.controller.response.TokenWithCommonResponse;
import com.skhuedin.skhuedin.domain.User;
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

        UserSaveRequestDto user = oauthService.requestAccessToken(socialLoginType, oAuthToken);
        String token = Strings.EMPTY;

        Long id = null;
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

        UserMainResponseDto responseDto = new UserMainResponseDto(userService.findByEmail(user.getEmail()));
        // user 인증을 위한 자체 토큰을 발급받아  저장,데이터에 user 값도 저장 해서 보냄
        return ResponseEntity.status(HttpStatus.OK).body((new CheckTokenWithCommonResponse<>(responseDto, token, isFirstVisit)));
    }
}