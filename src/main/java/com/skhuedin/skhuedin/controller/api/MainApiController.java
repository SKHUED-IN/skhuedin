package com.skhuedin.skhuedin.controller.api;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.CommonResponse;
import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.main.MainResponseDto;
import com.skhuedin.skhuedin.service.CategoryService;
import com.skhuedin.skhuedin.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainApiController {

    private final PostsService postsService;
    private final CategoryService categoryService;

    @GetMapping("main")
    public ResponseEntity<? extends BasicResponse> main(Pageable pageable) {
        List<CategoryMainResponseDto> categories = categoryService.findByWight();
        List<MainResponseDto> mainPosts = new ArrayList<>();

        for (CategoryMainResponseDto category : categories) {
            mainPosts.add(new MainResponseDto(
                    category.getName(),
                    category.getWeight(),
                    postsService.findByCategoryId(category.getId(), pageable)));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(mainPosts));
    }
}