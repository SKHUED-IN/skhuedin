package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.infra.LoginRequest;
import com.skhuedin.skhuedin.infra.TokenResponse;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class BaseController {

    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "kakaoLogin";
    }

}
