package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.dto.comment.CommentMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsAdminResponseDto;
import com.skhuedin.skhuedin.dto.question.QuestionMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.CategoryService;
import com.skhuedin.skhuedin.service.CommentService;
import com.skhuedin.skhuedin.service.PostsService;
import com.skhuedin.skhuedin.service.QuestionService;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

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

    @PostMapping("/user/delete")
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
    @PostMapping("/question/detail")
    public List<CommentMainResponseDto> commentMainList(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        return commentService.findByQuestionId(id);
    }

    @PostMapping("update/category")
    public String updateCategory(@RequestParam(value = "category", required = false, defaultValue = "0") Long category,
                                 @RequestParam(value = "post_id", required = false, defaultValue = "0") Long post_id) {
        postsService.update(post_id, category);
        return "redirect:/postList";
    }

    @PostMapping("category/up")
    public String upCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        categoryService.addWeight(id);
        return "redirect:/categoryList";
    }

    @PostMapping("category/down")
    public String downCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        categoryService.subtractWeight(id);
        return "redirect:/categoryList";
    }

    @PostMapping("create/category")
    public String createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        categoryService.save(categoryRequestDto);
        return "redirect:/categoryList";
    }

    @PostMapping("user/role")
    public String userRoleChange(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        userService.updateRole(id);
        return "redirect:/userList";
    }

    @PostMapping("question/status")
    public String questionStatus(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        questionService.updateStatus(id);
        return "redirect:/questionList";
    }

    @PostMapping("category/delete")
    public String deleteCategory(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        categoryService.delete(id);
        return "redirect:/categoryList";
    }

    @PostMapping("post/delete")
    public String deletePost(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        postsService.deletePostAdmin(id);
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
        for (CategoryMainResponseDto category : list) {
            category.add(postsService.findByCategoryId(category.getId()));
        }
        return list;
    }

    @GetMapping("/")
    public String main() {
        return "home";
    }

}