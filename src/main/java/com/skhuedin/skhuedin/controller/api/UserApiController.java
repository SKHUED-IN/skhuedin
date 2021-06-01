package com.skhuedin.skhuedin.controller.api;

import com.skhuedin.skhuedin.common.exception.EmptyTokenException;
import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.controller.response.TokenWithCommonResponse;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserSaveRequestDto;
import com.skhuedin.skhuedin.dto.user.UserTokenValidationDto;
import com.skhuedin.skhuedin.dto.user.UserUpdateDto;
import com.skhuedin.skhuedin.infra.JwtTokenProvider;
import com.skhuedin.skhuedin.infra.LoginRequest;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserApiController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("users/{userId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("userId") Long id) {

        UserMainResponseDto responseDto = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

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
        User user = userService.findByEmail(loginRequest.getEmail());
        UserMainResponseDto responseDto = new UserMainResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new TokenWithCommonResponse<>(responseDto, "Bearer " + token));
    }

    @PostMapping("token/validate")
    public ResponseEntity<? extends BasicResponse> validate(@RequestBody UserTokenValidationDto requestDto) {
        String token = requestDto.getToken();
        if (token.contains("Bearer")) {
            token = token.split("Bearer ")[1];
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(jwtTokenProvider.validateToken(token)));
    }
}