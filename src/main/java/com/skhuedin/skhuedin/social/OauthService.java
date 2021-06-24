package com.skhuedin.skhuedin.social;

import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.social.kakao.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final List<SocialOauth> socialOauthList;

    public UserSaveRequestDto requestAccessToken(String socialLoginType, OAuthToken oAuthToken) {
        SocialOauth socialOauth = findSocialOauthByType(socialLoginType);
        UserSaveRequestDto user = socialOauth.requestAccessToken(oAuthToken);
        return user;
    }

    public void logout(String socialLoginType, OAuthToken oAuthToken) {
        SocialOauth socialOauth = findSocialOauthByType(socialLoginType);
        socialOauth.logout(oAuthToken);
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