package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.infra.MyRole;
import com.skhuedin.skhuedin.infra.Role;
import com.skhuedin.skhuedin.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminApiController {

    private final PostsService postsService;

//    @MyRole(role = Role.ADMIN)
    @GetMapping("posts")
    public ResponseEntity<? extends BasicResponse> getPosts(
            Pageable pageable,
            @RequestParam(name = "category", defaultValue = "") String categoryName,
            @RequestParam(name = "username", defaultValue = "") String username) {

        if (categoryName.isEmpty() && !username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(postsService.findByUserName(pageable, username)));
        } else if (!categoryName.isEmpty() && username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(postsService.findByCategoryName(pageable, categoryName)));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse<>(postsService.findAll(pageable)));
        }
    }
}