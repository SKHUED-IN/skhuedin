package com.skhuedin.skhuedin.security;

import com.skhuedin.skhuedin.domain.user.User;
import com.skhuedin.skhuedin.dto.token.TokenMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.jwt.TokenProvider;
import com.skhuedin.skhuedin.repository.UserRepository;
import com.skhuedin.skhuedin.social.dto.UserInfo;
import com.skhuedin.skhuedin.social.service.GoogleService;
import com.skhuedin.skhuedin.social.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    public static final String GOOGLE = "google";
    public static final String KAKAO = "kakao";

    private final GoogleService googleService;
    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public TokenMainResponseDto login(String socialLoginType, String accessToken) {

        UserInfo userInfo = null;
        if (socialLoginType.equals(GOOGLE)) {
            userInfo = googleService.getUserInfo(accessToken);
        } else if (socialLoginType.equals(KAKAO)) {
            userInfo = kakaoService.getUserInfo(accessToken);
        }

        User user = userRepository.findByEmail(userInfo.getEmail()).orElse(null);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userInfo.getEmail(), "");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String jwt = tokenProvider.createToken(authentication);

        // 최초 로그인 시도인 경우
        if (user == null) {
            userRepository.save(user);
            return new TokenMainResponseDto(new UserMainResponseDto(userRepository.save(user)), jwt, false);
        }

        user.update(userInfo.toEntity());
        return new TokenMainResponseDto(new UserMainResponseDto(user), jwt, true);
    }
}