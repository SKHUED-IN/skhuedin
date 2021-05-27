package com.skhuedin.skhuedin.dto.category;

import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.service.CategoryService;
import com.skhuedin.skhuedin.service.PostsService;
import lombok.Getter;

@Getter

public class CategoryMainResponseDto {

    PostsService postsService;

    private Long id;
    private String name;
    private Long weight;
    private Long referPostCount;

    public CategoryMainResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.weight = category.getWeight();
    }

    public void add(Long referPostCount) {
        this.referPostCount = referPostCount;
    }
}
