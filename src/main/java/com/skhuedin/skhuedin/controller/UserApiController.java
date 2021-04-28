package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "kakaoLogin";
    }

    @PostMapping("login")
    public ResponseEntity<? extends BasicResponse> login(@RequestBody UserMainResponseDto requestDto) {

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        User user = userService.login(requestDto);
        header.add("Authorization", "Bearer " + userService.signIn(user));
        header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        return new ResponseEntity<>(new CommonResponse<>(user),header,HttpStatus.OK);

    }
}
