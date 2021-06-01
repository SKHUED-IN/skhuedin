package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserAdminApiController {

    private final UserService userService;

    @MyRole(role = Role.ADMIN)
    @ResponseBody
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public List<UserMainResponseDto> userList() {
        List<UserMainResponseDto> list = userService.findAll();
        return list;
    }

    @PostMapping("user/role")
    public String userRoleChange(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        userService.updateRole(id);
        return "redirect:/userList";
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
}