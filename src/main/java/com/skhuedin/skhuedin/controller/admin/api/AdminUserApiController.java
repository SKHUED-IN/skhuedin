package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.posts.PostsAdminUpdateRequestDto;
import com.skhuedin.skhuedin.dto.user.UserAdminUpdateRequestDto;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserApiController {
    private final UserService userService;

    //@MyRole(role = Role.ADMIN)
    @GetMapping("users")
    public ResponseEntity<? extends BasicResponse> getUsers(
            Pageable pageable,
            @RequestParam(name = "role", defaultValue = "") String role,
            @RequestParam(name = "username", defaultValue = "") String username
    ) {

        if (username.isEmpty() && !role.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(userService.findByUserRole(pageable, role)));
        } else if (!username.isEmpty() && role.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(userService.findByUserName(pageable, username)));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(userService.findAll(pageable)));
        }
    }

    //    @MyRole(role = Role.ADMIN)
    @GetMapping("users/{userId}")
    public ResponseEntity<? extends BasicResponse> getUsers(@PathVariable("userId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(userService.findByIdByAdmin(id)));
    }

    //    @MyRole(role = Role.ADMIN)
    @PutMapping("users/{userId}")
    public ResponseEntity<? extends BasicResponse> updateUsers(
            @RequestBody UserAdminUpdateRequestDto requestDto) {
        userService.updateRole(requestDto.getId(), requestDto.getRole());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/users/delete/{userId}")
    public ResponseEntity<? extends BasicResponse> deleteUsers(@PathVariable("userId") Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}