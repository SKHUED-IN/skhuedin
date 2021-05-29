package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.Category;
import com.skhuedin.skhuedin.domain.Provider;
import com.skhuedin.skhuedin.domain.User;
import com.skhuedin.skhuedin.dto.blog.BlogSaveRequestDto;
import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/truncate.sql")
class CategoryServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    Category category;

    @BeforeEach
    void beforeEach() {
        category = Category.builder()
                .name("연봉")
                .weight(4L)
                .build();

        categoryRepository.save(category);
    }

    @DisplayName("category DTO 를 받아 저장하는 로직")
    @Test
    void save() {
        //given 어떤 값이 주어지고
        CategoryRequestDto requestDto = generateCategory();
        Long saveId = categoryService.save(requestDto);

        //when 무엇을 했을 때
        CategoryMainResponseDto responseDto = categoryService.findById(saveId);

        //then 어떤 값을 원한다.
        assertAll(
                () -> assertEquals(saveId, responseDto.getId()),
                () -> assertEquals(requestDto.getName(), responseDto.getName()),
                () -> assertEquals(requestDto.getWeight(), responseDto.getWeight())
        );
    }

    @DisplayName("삭제가 잘 되는지 테스트")
    @Test
    void delete() {
        //given 어떤 값이 주어지고
        //given 어떤 값이 주어지고
        CategoryRequestDto requestDto = generateCategory();
        Long saveId = categoryService.save(requestDto);

        //when 무엇을 했을 때
        categoryService.delete(saveId);

        //then 어떤 값을 원한다.
        assertThrows(IllegalArgumentException.class, () ->
                categoryService.findById(saveId)
        );
    }

    @DisplayName("가중치 증가 테스트")
    @Test
    void addWeight() {
        //given 어떤 값이 주어지고
        Long weight = category.getWeight();

        //when 무엇을 했을 때
        category.addWeight();
        category.addWeight();

        //then 어떤 값을 원한다.
        assertEquals(category.getWeight(), weight + 2L);
    }

    @DisplayName("가중치 감소 테스트")
    @Test
    void subtractWeight() {
        //given 어떤 값이 주어지고
        Long weight = category.getWeight();

        //when 무엇을 했을 때
        category.subtractWeight();
        category.subtractWeight();

        //then 어떤 값을 원한다.
        assertEquals(category.getWeight(), weight - 2L);
    }

    private CategoryRequestDto generateCategory() {
        return CategoryRequestDto.builder()
                .name("자기주도개발")
                .weight(6L)
                .build();
    }
}