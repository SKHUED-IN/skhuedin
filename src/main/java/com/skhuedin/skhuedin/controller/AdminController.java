package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsAdminResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.user.AdminSaveRequestDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.infra.LoginRequest;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;
    private final PostsService postsService;
    private final QuestionService questionService;
    private final CategoryService categoryService;


    @ResponseBody
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public List<UserMainResponseDto> userList() {
        List<UserMainResponseDto> list = userService.findAll();
        return list;
    }

    @GetMapping("/userList")
    public String userMainList() {
        return "contents/userList";
    }

    @PostMapping("/userDelete")
    public String userDelete(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        userService.delete(id);
        return "redirect:/userList";
    }

    @GetMapping("/postList")
    public String postMainList() {
        return "contents/postList";
    }

    @GetMapping("/questionList")
    public String questionMainList() {
        return "contents/questionList";
    }

    @GetMapping("/categoryList")
    public String categoryMainList() {
        return "contents/categoryList";
    }

    @PostMapping("update/category")
    public String updateCategory(@RequestParam(value = "category", required = false, defaultValue = "0") Long category,
                                 @RequestParam(value = "post_id", required = false, defaultValue = "0") Long post_id) {
        postsService.update(post_id, category);
        return "redirect:/postList";
    }

    @PostMapping("categoryList/up")
    public String upCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        categoryService.addWeight(id);
        return "redirect:/categoryList";
    }

    @PostMapping("categoryList/down")
    public String downCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        categoryService.subtractWeight(id);
        return "redirect:/categoryList";
    }

    @PostMapping("postDelete")
    public String deleteCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        postsService.deleteAdmin(id);
        return "redirect:/postList";
    }

    @ResponseBody
    @RequestMapping(value = "/postList", method = RequestMethod.POST)
    public List<PostsAdminResponseDto> postList() {
        List<PostsAdminResponseDto> list = postsService.findAll();
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/questionList", method = RequestMethod.POST)
    public List<QuestionMainResponseDto> questionList() {
        List<QuestionMainResponseDto> list = questionService.findAll();
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/categoryList", method = RequestMethod.POST)
    public List<CategoryMainResponseDto> categoryList() {
        List<CategoryMainResponseDto> list = categoryService.findAll();
        return list;
    }

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


}