package com.skhuedin.skhuedin.controller.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.security.AuthService;
import com.skhuedin.skhuedin.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogApiController {

    private final BlogService blogService;
    private final AuthService authService;

    @PostMapping("blogs")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> save(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @Valid BlogSaveRequestDto requestDto) throws NoSuchAlgorithmException, IOException {

        if (!authService.isSameUser(requestDto.getUserId())) {
            throw new AccessDeniedException("일치하지 않는 user 정보 입니다.");
        }

        Long saveId = blogService.save(requestDto, file);

        PageRequest pageRequest = PageRequest.of(0, 10);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(blogService.findById(saveId, pageRequest)));
    }

    @GetMapping("blogs/{blogId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("blogId") Long id, Pageable pageable) {
        BlogMainResponseDto responseDto = blogService.findById(id, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @GetMapping("blogs")
    public ResponseEntity<? extends BasicResponse> findAll(
            @RequestParam(value = "cmd", defaultValue = "") String cmd,
            Pageable pageable) {

        Page<BlogMainResponseDto> blogs;
        if (cmd.equals("view")) {
            blogs = blogService.findAllOrderByPostsView(pageable);
        } else {
            blogs = blogService.findAll(pageable);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(blogs));
    }

    @PutMapping("blogs/{blogId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> update(
            @PathVariable("blogId") Long id,
            @RequestParam(name = "file", required = false) MultipartFile file,
            BlogSaveRequestDto updateDto) throws NoSuchAlgorithmException, IOException {

        if (!authService.isSameUser(updateDto.getUserId())) {
            throw new AccessDeniedException("일치하지 않는 user 정보 입니다.");
        }

        blogService.update(updateDto, file);

        PageRequest pageRequest = PageRequest.of(0, 10);
        BlogMainResponseDto responseDto = blogService.findById(id, pageRequest);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @DeleteMapping("blogs/{blogId}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("blogId") Long id) {

        BlogMainResponseDto blogMainResponseDto = blogService.findById(id);

        if (!authService.isSameUser(blogMainResponseDto.getUser().getId())) {
            throw new AccessDeniedException("일치하지 않는 user 정보 입니다.");
        }

        blogService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}