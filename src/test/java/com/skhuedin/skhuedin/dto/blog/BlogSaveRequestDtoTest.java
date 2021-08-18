package com.skhuedin.skhuedin.dto.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BlogSaveRequestDtoTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("BlogSaveRequestDto의 user id의 null 유무를 검증하는 테스트")
    void userIdIsNotNull() {

        // given
        BlogSaveRequestDto requestDto = BlogSaveRequestDto
                .builder()
                .userId(null)
                .content("책장입니다.")
                .build();

        // when
        Set<ConstraintViolation<BlogSaveRequestDto>> constraintViolations = validator.validate(requestDto);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("user id는 null이 될 수 없습니다.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("BlogSaveRequestDto의 content의 최대 길이를 검증하는 테스트")
    void contentMore1000() {

        // given
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 20001; i++) {
            stringBuilder.append(i);
        }

        BlogSaveRequestDto requestDto = BlogSaveRequestDto
                .builder()
                .userId(1L)
                .content(stringBuilder.toString())
                .build();

        // when
        Set<ConstraintViolation<BlogSaveRequestDto>> constraintViolations = validator.validate(requestDto);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("content의 size는 20000을 넘을 수 없습니다.", constraintViolations.iterator().next().getMessage());
    }
}