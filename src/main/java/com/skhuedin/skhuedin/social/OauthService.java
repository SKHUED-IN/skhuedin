package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
//    private final List<Provider> providerList;

    /**
     * 리다이렉트 된 페이지에서 소셜 서버에 가서 사용자의 아이디, 비밀번호 입력하면 일회용 코드를 발급해 줌.
     * 그 코드를 가지고 acce
     */
    public UserSaveRequestDto requestAccessToken(String socialLoginType, OAuthToken oAuthToken) {
        SocialOauth socialOauth = findSocialOauthByType(socialLoginType);
        UserSaveRequestDto user = socialOauth.requestAccessToken(oAuthToken);
        return user;
    }

    private SocialOauth findSocialOauthByType(String socialLoginType) {

        Provider provider = null;

        if (socialLoginType.equals("kakao")) {
            provider = Provider.KAKAO;
        } else if (socialLoginType.equals("google")) {
            provider = Provider.GOOGLE;
        } else if (socialLoginType.equals("naver")) {
            provider = Provider.NAVER;
        } else {
            throw new IllegalArgumentException("알 수 없는 SocialLoginType 입니다.");
        }

        Provider finalProvider = provider;
        return socialOauthList.stream()
                .filter(x -> x.type() == finalProvider)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialOauth 입니다."));
    }
}