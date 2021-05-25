package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsAdminResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.infra.LoginRequest;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
public class
AdminController {

    private final UserService userService;
    private final PostsService postsService;
    private final QuestionService questionService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @MyRole(role = Role.ADMIN)
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

    @GetMapping("/create/category")
    public String createCatetory() {
        return "contents/createCategory";
    }

    @ResponseBody
    @PostMapping("/questionDetail")
    public List<CommentMainResponseDto> commentMainList(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        return commentService.findByQuestionId(id);
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

    @PostMapping("create/category")
    public String createCategory( @RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.save(categoryRequestDto);
        return "redirect:/categoryList";
    }

    @PostMapping("postDelete")
    public String deleteCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        postsService.deleteAdmin(id);
        return "redirect:/postList";
    }

    @MyRole(role = Role.ADMIN)
    @ResponseBody
    @PostMapping("/postList")
    public List<PostsAdminResponseDto> postList() {
        List<PostsAdminResponseDto> list = postsService.findAll();
        return list;
    }

    @MyRole(role = Role.ADMIN)
    @ResponseBody
    @PostMapping("/questionList")
    public List<QuestionMainResponseDto> questionList() {
        List<QuestionMainResponseDto> list = questionService.findAll();
        return list;
    }

    @MyRole(role = Role.ADMIN)
    @ResponseBody
    @PostMapping("/categoryList")
    public List<CategoryMainResponseDto> categoryList() {
        List<CategoryMainResponseDto> list = categoryService.findAll();
        return list;
    }

    @GetMapping("/")
    public String main() {
        return "home";
    }

}