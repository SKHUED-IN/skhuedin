package com.skhuedin.skhuedin.controller.admin;

import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminMainController {

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
}