package com.skhuedin.skhuedin.social.kakao;

import com.skhuedin.skhuedin.domain.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    @GetMapping("/")
    public String main() {
        return "kakaoLogin";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code, Model model) {
        User user = kakaoService.saveKakaoUser(kakaoService.getProfile(
                kakaoService.oAuthToken(code)));
        model.addAttribute("user", user);
        return "kakaoProfile";
    }
}
