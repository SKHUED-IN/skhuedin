package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/truncate.sql")
@Transactional
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    Category category1;
    Category category2;

    @BeforeEach
    void beforeEach() {
        category1 = Category.builder()
                .name("건의사항")
                .weight(0L)
                .build();

        categoryRepository.save(category1);

        category2 = Category.builder()
                .name("하고싶은말")
                .weight(10L)
                .build();

        categoryRepository.save(category2);
    }

    @Test
    @DisplayName("category의 weight 내림차순으로 조회하는 테스트")
    void findByWeight() {

        // given & when
        List<Category> categories = categoryRepository.findByWeight();

        // then
        assertAll(
                () -> assertEquals(categories.size(), 1),
                () -> assertEquals(categories.get(0).getWeight(), 10L)
        );
    }

    @Test
    @DisplayName("category의 이름으로 조회하는 테스트")
    void findByCategoryName() {

        // given
        String categoryName = "건의사항";

        // when
        Category category = categoryRepository.findByCategoryName(categoryName).get();

        // then
        assertEquals(category.getName(), categoryName);
    }
}