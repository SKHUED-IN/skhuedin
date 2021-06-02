package com.skhuedin.skhuedin.dto.posts;

import lombok.Getter;

@Getter
public class PostsAdminUpdateRequestDto {

    private Long id;
    private Long categoryId;
    private Boolean deleteStatus;
}