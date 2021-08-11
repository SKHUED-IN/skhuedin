package com.skhuedin.skhuedin.controller.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.user.UserMainResponseDto;
import com.skhuedin.skhuedin.dto.user.UserTokenValidationDto;
import com.skhuedin.skhuedin.dto.user.UserUpdateDto;
import com.skhuedin.skhuedin.jwt.TokenProvider;
import com.skhuedin.skhuedin.service.BlogService;
import com.skhuedin.skhuedin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final BlogService blogService;
    private final TokenProvider tokenProvider;

    @GetMapping("users/{userId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("userId") Long id) {

        UserMainResponseDto responseDto = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @PostMapping("users/{userId}")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')") 추가적인 검증이 요망
    public ResponseEntity<? extends BasicResponse> update(@PathVariable("userId") Long id,
                                                          @Valid @RequestBody UserUpdateDto updateDto) {
        userService.updateYearData(id, updateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("users")
    public ResponseEntity<? extends BasicResponse> findAll() {

        List<UserMainResponseDto> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(users));
    }

    @GetMapping("users/{userId}/blogs")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> blogByUserId(@PathVariable("userId") Long userId) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(blogService.findByUserId(userId)));
    }

    @PostMapping("token/validate")
    public ResponseEntity<? extends BasicResponse> validate(@RequestBody UserTokenValidationDto requestDto) {

        String token = requestDto.getToken();
        if (token.contains("Bearer")) {
            token = token.split("Bearer ")[1];
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(tokenProvider.validateToken(token)));
    }
}