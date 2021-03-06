package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.domain.user.Role;
import com.skhuedin.skhuedin.dto.user.UserAdminUpdateRequestDto;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserApiController {
    private final UserService userService;

    @GetMapping("users")
    public ResponseEntity<? extends BasicResponse> getUsers(
            Pageable pageable,
            @RequestParam(name = "role", defaultValue = "") Role role,
            @RequestParam(name = "username", defaultValue = "") String username) {

        if (username.isEmpty() && role != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(userService.findByUserRole(pageable, role)));
        } else if (!username.isEmpty() && role == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(userService.findByUserName(pageable, username)));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(userService.findAll(pageable)));
        }
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<? extends BasicResponse> getUser(@PathVariable("userId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(userService.toUserAdminMainResponseDto(id)));
    }

    @PutMapping("users")
    public ResponseEntity<? extends BasicResponse> updateUserRole(@RequestBody UserAdminUpdateRequestDto requestDto) {
        userService.updateRole(requestDto.getId(), requestDto.getRole());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<? extends BasicResponse> deleteUser(@PathVariable("userId") Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
