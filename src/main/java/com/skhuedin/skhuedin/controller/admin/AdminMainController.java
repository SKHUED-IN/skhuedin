package com.skhuedin.skhuedin.controller.admin;

import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMainController {

    @GetMapping
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/main");
        return modelAndView;
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("posts")
    public ModelAndView posts() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/posts");
        return modelAndView;
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("posts/detail")
    public ModelAndView postsDetail() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/posts-detail");
        modelAndView.setViewName("admin/posts-detail");
        return modelAndView;
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("question")
    public ModelAndView question() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/question");
        return modelAndView;
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("question/detail")
    public ModelAndView questionDetail() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/question-detail");
        return modelAndView;
    }

    @GetMapping("users")
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/users");
        return modelAndView;
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("users/detail")
    public ModelAndView usersDetail() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/users-detail");
        return modelAndView;
    }

    @GetMapping("category")
    public ModelAndView category() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/category");
        return modelAndView;
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("category/detail")
    public ModelAndView categoryDetail() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/category-detail");
        return modelAndView;
    }

    @GetMapping("category/create")
    public ModelAndView categoryCreate() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/create-category");
        return modelAndView;
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("suggestions")
    public ModelAndView suggestions() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/suggestions");
        return modelAndView;
    }

    @GetMapping("blogs")
    public ModelAndView blogs() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/blogs");
        return modelAndView;
    }
}