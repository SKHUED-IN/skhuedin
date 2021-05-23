package com.skhuedin.skhuedin.dto.category;

import com.skhuedin.skhuedin.domain.Category;
import lombok.Getter;

@Getter
public class CategoryMainResponseDto {

    private Long id;
    private String name;
    private Integer weight;

    public CategoryMainResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.weight = category.getWeight();
    }
}
