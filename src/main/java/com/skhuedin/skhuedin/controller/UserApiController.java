package com.skhuedin.skhuedin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    @GetMapping("/")
    public String main() {
        return "kakaoLogin";
    }

}
