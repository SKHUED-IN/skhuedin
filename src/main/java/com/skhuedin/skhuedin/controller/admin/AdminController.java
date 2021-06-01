package com.skhuedin.skhuedin.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @GetMapping("/")
    public String main() {
        return "home";
    }

}