package com.skhuedin.skhuedin.dto.category;

import com.skhuedin.skhuedin.domain.category.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CategoryRequestDto {

    private String name;
    private Long weight;

    @Builder
    public CategoryRequestDto(String name, Long weight) {
        this.name = name;
        this.weight = weight;
    }

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .weight(this.weight)
                .build();
    }
}
