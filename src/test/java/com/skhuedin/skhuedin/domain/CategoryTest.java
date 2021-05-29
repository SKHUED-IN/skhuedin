package com.skhuedin.skhuedin.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private Category category;

    @DisplayName("카테고리 생성 로직")
    @BeforeEach
    void saveCategory() {

        category = Category.builder()
                .name("연봉")
                .weight(4L)
                .build();
    }


    @DisplayName("기존 카테고리를 수정하는 로직")
    @Test
    void updateCategory() {
        //given 어떤 값이 주어지고

        Category newCategory = Category.builder()
                .name("협상")
                .weight(5L)
                .build();

        //when 무엇을 했을 때
        category.updateCategory(newCategory);

        //then 어떤 값을 원한다.
    }

    @DisplayName("가중치 증가")
    @Test
    void addWeight() {
        //given 어떤 값이 주어지고 //when 무엇을 했을 때
        category.addWeight();

        //then 어떤 값을 원한다.
        assertEquals(category.getWeight(), 5L);

    }

    @Test
    void subtractWeight() {
        //given 어떤 값이 주어지고 //when 무엇을 했을 때
        Long weight = category.getWeight();
        category.subtractWeight();

        //then 어떤 값을 원한다.
        assertEquals(category.getWeight(), weight - 1L);
    }

}