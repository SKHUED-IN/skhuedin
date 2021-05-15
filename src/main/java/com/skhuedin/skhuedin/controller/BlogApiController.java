package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.blog.BlogMainResponseDto;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("blogs")
    public ResponseEntity<? extends BasicResponse> save(@RequestBody BlogSaveRequestDto requestDto) {
        Long saveId = blogService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(blogService.findById(saveId)));
    }

    @GetMapping("blogs/{blogId}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable("blogId") Long id) {
        BlogMainResponseDto responseDto = blogService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @GetMapping("blogs")
    public ResponseEntity<? extends BasicResponse> findAll(@RequestParam(value = "cmd", defaultValue = "") String cmd,
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
    public ResponseEntity<? extends BasicResponse> update(@PathVariable("blogId") Long id,
                                                          @RequestBody BlogSaveRequestDto updateDto) {
        Long blogId = blogService.update(id, updateDto);
        BlogMainResponseDto responseDto = blogService.findById(blogId);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(responseDto));
    }

    @DeleteMapping("blogs/{blogId}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("blogId") Long id) {
        blogService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}