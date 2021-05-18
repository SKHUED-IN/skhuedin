package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.AdminSaveRequestDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.infra.LoginRequest;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "board/login";
    }


    @PostMapping("/board/main")
    public String signin(HttpServletResponse response, LoginRequest requestDto, RedirectAttributes redirectAttribute) {

        UserMainResponseDto user = new UserMainResponseDto(userService.findByEmail(requestDto.getEmail()));
        String token = userService.adminSignIn(requestDto);
        redirectAttribute.addFlashAttribute("user", user);

        // 30분후 만료되는 jwt 만들어서 쿠키에 저장
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);

        return "redirect:/board/main";
    }


    @GetMapping("/board/main")
    public String sign(HttpServletRequest request, Model model, HttpServletResponse response) {

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        UserMainResponseDto dto = (UserMainResponseDto) inputFlashMap.get("user");
//        String token = (String)inputFlashMap.get("Authorization");
//        response.addHeader("Authorization",token);
        // 30분후 만료되는 jwt 만들어서 쿠키에 저장
//        Cookie cookie = new Cookie("Authorization", token);
//        cookie.setPath("/");
//        cookie.setMaxAge(Integer.MAX_VALUE);
//        response.addCookie(cookie);


        model.addAttribute("user", dto);
        return "board/main";

    }

    @GetMapping("/board/userList")
    public String findAll(HttpServletRequest request, Model model) {
        List<UserMainResponseDto> list = userService.findAll();
        model.addAttribute("list", list);
        return "board/userList";
    }

    @GetMapping("/board/userList/{userId}")
    public String userDetail(@PathVariable("userId") Long id, Model model) {
        UserMainResponseDto user = userService.findById(id);
        model.addAttribute("user", user);
        return "board/userDetail";
    }

    @PostMapping("/board/delete/{userId}")
    public String userDelete(@PathVariable("userId") Long id) {
        userService.delete(id);
        return "redirect:/board/userList";
    }
}