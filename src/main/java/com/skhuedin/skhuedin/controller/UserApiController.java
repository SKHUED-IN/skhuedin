package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "kakaoLogin";
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("userId") Long id) {
        UserMainResponseDto responseDto = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @PostMapping("users/{userId}")
    public ResponseEntity<? extends BasicResponse> updateUserAddInfo(@PathVariable("userId") Long id, @RequestBody UserSaveRequestDto requestDto) {
        userService.updateInfo(id, requestDto);
        UserMainResponseDto responseDto = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @GetMapping("users")
    public ResponseEntity<? extends BasicResponse> findAll() {
        List<UserMainResponseDto> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(users));
    }
}
