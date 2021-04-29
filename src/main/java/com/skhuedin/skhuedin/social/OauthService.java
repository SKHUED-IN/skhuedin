package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthService {

    /**
     * 먼저 Field에서 @RequiredArgsConstructor를 통해 SocialOauth
     * 타입의 객체들이 List 형태로 Injection 되도록 필드를 List 타입으로 수정한 후
     * 그다음 SocialLoginType에 맞는 SocialOauth 객체를 반환하는
     * findSocialOauthByType 메소드 생성하여 각 request, requestAccessToken
     * 메소드에서 SocialLoginType에 맞는 SocialOauth 클래스를
     * findSocialOauthByType 함수를 통해 초기화되도록 수정
     */
    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;

    public void request(Provider socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            //페이지를 아래 주소로 리다이렉트 지시
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            log.info("redirectURL을 확인해보세요");
            e.printStackTrace();
        }
    }

    /**
     * 리다이렉트 된 페이지에서 소셜 서버에 가서 사용자의 아이디, 비밀번호 입력하면 일회용 코드를 발급해 줌.
     * 그 코드를 가지고 acce
     */
    //
    public UserMainResponseDto requestAccessToken(Provider socialLoginType, String code) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        User user =  socialOauth.requestAccessToken(code);
        return new UserMainResponseDto(user);
    }

    private SocialOauth findSocialOauthByType(Provider socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}