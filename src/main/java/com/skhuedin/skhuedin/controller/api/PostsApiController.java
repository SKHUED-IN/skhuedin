package com.skhuedin.skhuedin.controller.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import com.skhuedin.skhuedin.dto.posts.PostsSaveRequestDto;
import com.skhuedin.skhuedin.dto.posts.SuggestionsSaveRequestDto;
import com.skhuedin.skhuedin.security.AuthService;
import com.skhuedin.skhuedin.service.BlogService;
import com.skhuedin.skhuedin.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostsApiController {

    private final PostsService postsService;
    private final BlogService blogService;
    private final AuthService authService;

    @PostMapping("posts")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> save(@Valid @RequestBody PostsSaveRequestDto requestDto) {

        BlogMainResponseDto blogMainResponseDto = blogService.findById(requestDto.getBlogId());

        if (!authService.isSameUser(blogMainResponseDto.getUser().getId())) {
            throw new AccessDeniedException("일치하지 않은 user 정보입니다.");
        }

        Long saveId = postsService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(postsService.findById(saveId)));
    }

    @GetMapping("posts/{postsId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("postsId") Long id) {
        PostsMainResponseDto responseDto = postsService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @PutMapping("posts/{postsId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> update(@PathVariable("postsId") Long id,
                                                          @Valid @RequestBody PostsSaveRequestDto updateDto) {

        PostsMainResponseDto postsMainResponseDto = postsService.findById(id);
        if (!authService.isSameUser(postsMainResponseDto.getUser().getId())) {
            throw new AccessDeniedException("일치하지 않은 user 정보입니다.");
        }

        Long postsId = postsService.update(id, updateDto);
        PostsMainResponseDto responseDto = postsService.findById(postsId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @DeleteMapping("posts/{postsId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("postsId") Long id) {

        PostsMainResponseDto postsMainResponseDto = postsService.findById(id);
        if (!authService.isSameUser(postsMainResponseDto.getUser().getId())) {
            throw new AccessDeniedException("일치하지 않은 user 정보입니다.");
        }

        postsService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("blogs/{blogId}/posts")
    public ResponseEntity<? extends BasicResponse> findByBlogId(@PathVariable("blogId") Long id, Pageable pageable) {
        Page<PostsMainResponseDto> posts = postsService.findByBlogId(id, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(posts));
    }

    @PostMapping("suggestions")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> saveSuggestions(@RequestBody SuggestionsSaveRequestDto requestDto) {

        Long id = postsService.saveSuggestions(requestDto);
        PostsMainResponseDto responseDto = postsService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }
}