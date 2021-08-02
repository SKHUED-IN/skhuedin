package com.skhuedin.skhuedin.controller.admin.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.posts.PostsAdminUpdateRequestDto;
import com.skhuedin.skhuedin.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Slf4j
public class AdminPostsApiController {

    private final PostsService postsService;

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

    @GetMapping("posts/{postsId}")
    public ResponseEntity<? extends BasicResponse> getPosts(@PathVariable("postsId") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse<>(postsService.findByIdByAdmin(id)));
    }

    @PutMapping("posts/{postsId}")
    public ResponseEntity<? extends BasicResponse> updatePosts(@RequestBody PostsAdminUpdateRequestDto requestDto) {
        postsService.update(requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}