package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.category.Category;
import com.skhuedin.skhuedin.dto.category.CategoryMainResponseDto;
import com.skhuedin.skhuedin.dto.category.CategoryRequestDto;
import com.skhuedin.skhuedin.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql("/truncate.sql")
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    Category category;

    @BeforeEach
    void beforeEach() {
        category = Category.builder()
                .name("x")
                .weight(4L)
                .build();

        categoryRepository.save(category);
    }

    @DisplayName("category DTO 를 받아 저장하는 로직")
    @Test
    void save() {
        //given 어떤 값이 주어지고
        CategoryRequestDto requestDto = generateCategory();
        Long saveId = categoryRepository.save(requestDto.toEntity()).getId();

        //when 무엇을 했을 때
        CategoryMainResponseDto responseDto = categoryService.findById(saveId);

        //then 어떤 값을 원한다.
        assertAll(
                () -> assertEquals(saveId, responseDto.getId()),
                () -> assertEquals(requestDto.getName(), responseDto.getName()),
                () -> assertEquals(requestDto.getWeight(), responseDto.getWeight())
        );
    }

    @DisplayName("category 를 조건 없이 전체 조회할 때")
    @Test
    void findAll() {
        //given 어떤 값이 주어지고
        CategoryRequestDto requestDto = generateCategory();
        Long saveId = categoryRepository.save(requestDto.toEntity()).getId();

        //when 무엇을 했을 때
        List<CategoryMainResponseDto> list = categoryService.findAll();

        //then 어떤 값을 원한다.
        assertNotNull(list);
    }

    @DisplayName("category 를 findAll_paging 를 하여 전체 조회할 때")
    @Test
    void findAll_paging() {
        //given 어떤 값이 주어지고
        Pageable pageable = PageRequest.of(0, 10);

        CategoryRequestDto requestDto = generateCategory();
        Long saveId = categoryRepository.save(requestDto.toEntity()).getId();

        //when 무엇을 했을 때
        Page<CategoryMainResponseDto> list = categoryService.findAll(pageable);

        //then 어떤 값을 원한다.
        assertNotNull(list);
    }

    @DisplayName("category 단건 조회")
    @Test
    void findById() {
        //given 어떤 값이 주어지고
        CategoryRequestDto requestDto = generateCategory();
        Long saveId = categoryRepository.save(requestDto.toEntity()).getId();

        //when 무엇을 했을 때
        CategoryMainResponseDto category = categoryService.findById(saveId);

        //then 어떤 값을 원한다.
        assertAll(
                () -> assertEquals(saveId, category.getId()),
                () -> assertEquals(requestDto.getName(), category.getName()),
                () -> assertEquals(requestDto.getWeight(), category.getWeight())
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

    @DisplayName("삭제가 잘 되는지 테스트")
    @Test
    void delete() {
        //given 어떤 값이 주어지고
        CategoryRequestDto requestDto = generateCategory();
        Long saveId = categoryRepository.save(requestDto.toEntity()).getId();

        //when 무엇을 했을 때
        categoryService.delete(saveId);

        //then 어떤 값을 원한다.
        assertThrows(IllegalArgumentException.class, () ->
                categoryService.findById(saveId)
        );
    }

    @AfterEach
    void afterEach() {
        categoryRepository.deleteAll();
    }

    private CategoryRequestDto generateCategory() {
        return CategoryRequestDto.builder()
                .name("자기주도개발")
                .weight(6L)
                .build();
    }
}
