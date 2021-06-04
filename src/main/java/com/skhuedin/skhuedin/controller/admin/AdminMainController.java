package com.skhuedin.skhuedin.controller.admin;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMainController {
    private final UserService userService;

    @GetMapping
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-main");
        return modelAndView;
    }

//    @MyRole(role = Role.ADMIN)
    @GetMapping("posts")
    public ModelAndView posts() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-posts");
        return modelAndView;
    }

//    @MyRole(role = Role.ADMIN)
    @GetMapping("posts/detail")
    public ModelAndView postsDetail() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-posts-detail");
        return modelAndView;
    }

    @GetMapping("users")
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-users");
        return modelAndView;
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("users/detail")
    public ModelAndView usersDetail() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-users-detail");
        return modelAndView;
    }

    @GetMapping("category")
    public ModelAndView category() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-category");
        return modelAndView;
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("category/detail")
    public ModelAndView categoryDetail() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-category-detail");
        return modelAndView;
    }

    @GetMapping("category/create")
    public ModelAndView categoryCreate() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-create-category");
        return modelAndView;
    }
}