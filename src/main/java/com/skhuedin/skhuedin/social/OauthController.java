package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.token.AccessTokenRequestDto;
import com.skhuedin.skhuedin.dto.token.TokenMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.service.UserService;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            @RequestBody AccessTokenRequestDto requestDto) {

        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(requestDto.getAccessToken());

        UserSaveRequestDto user = oauthService.requestAccessToken(socialLoginType, oAuthToken);

        boolean isFirstVisit = false;

        if (userService.findByEmail(user.getEmail()) == null) {
            userService.signUp(user);
            isFirstVisit = true;
        } else if (userService.findByEmail(user.getEmail()).getEntranceYear() == null) {
            isFirstVisit = true;
        }

        //회원이면 로그인을 시킴
        String token = userService.signIn(user);

        UserMainResponseDto responseDto = new UserMainResponseDto(userService.findByEmail(user.getEmail()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(new TokenMainResponseDto(responseDto, token, isFirstVisit)));
    }

    @PostMapping("/{socialLoginType}/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(
            @PathVariable("socialLoginType") String socialLoginType,
            @RequestBody AccessTokenRequestDto requestDto) {

        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(requestDto.getAccessToken());

        oauthService.logout(socialLoginType, oAuthToken);
    }
}