package com.skhuedin.skhuedin.dto.main;

import com.skhuedin.skhuedin.dto.posts.PostsMainResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class MainResponseDto {

    private String categoryName;
    private Long weight;
    private List<PostsMainResponseDto> posts;

    public MainResponseDto(String categoryName, Long weight, List<PostsMainResponseDto> posts) {
        this.categoryName = categoryName;
        this.weight = weight;
        this.posts = posts;
    }
}