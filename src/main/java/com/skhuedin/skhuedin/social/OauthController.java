package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.token.AccessTokenRequestDto;
import com.skhuedin.skhuedin.dto.token.TokenMainResponseDto;
import com.skhuedin.skhuedin.security.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class OauthController {

    private final AuthService authService;

    @PostMapping("/{socialLoginType}/callback")
    public ResponseEntity<? extends BasicResponse> callback(
            @PathVariable("socialLoginType") String socialLoginType, @RequestBody AccessTokenRequestDto requestDto) {

        TokenMainResponseDto tokenMainResponseDto = authService.login(socialLoginType, requestDto.getAccessToken());

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(tokenMainResponseDto));
    }
}