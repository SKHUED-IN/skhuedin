package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.controller.response.TokenWithCommonResponse;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.dto.user.UserUpdateDto;
import com.skhuedin.skhuedin.infra.LoginRequest;
import com.skhuedin.skhuedin.infra.MyRole;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("users/{userId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("userId") Long id) {
        UserMainResponseDto responseDto = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @MyRole
    @PostMapping("users/{userId}")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable("userId") Long id,
                                                          @Valid @RequestBody UserUpdateDto updateDto) {
        userService.update(id, updateDto);
        User user = userService.getUser(id);
        UserSaveRequestDto requestDto = new UserSaveRequestDto(user);
        String token = userService.signIn(requestDto);
        UserMainResponseDto responseDto = new UserMainResponseDto(user);

        return ResponseEntity.status(HttpStatus.OK).body((new TokenWithCommonResponse<>(responseDto, token)));

    }

    @GetMapping("users")
    public ResponseEntity<? extends BasicResponse> findAll() {
        List<UserMainResponseDto> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(users));
    }

    @PostMapping("token")
    public ResponseEntity<? extends BasicResponse> token(@RequestBody LoginRequest loginRequest) {
        String token = userService.adminSignIn(loginRequest);
        User user = userService.findByEmail(loginRequest.getName());
        UserMainResponseDto responseDto = new UserMainResponseDto(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new TokenWithCommonResponse<>(responseDto, "Bearer " + token));
    }
}