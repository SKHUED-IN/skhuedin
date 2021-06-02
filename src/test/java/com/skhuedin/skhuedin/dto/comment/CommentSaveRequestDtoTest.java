package com.skhuedin.skhuedin.dto.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommentSaveRequestDtoTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("CommentSaveRequestDto를 검증하는 테스트")
    void testValidation() {

        // given
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 1001; i++) {
            stringBuilder.append(i);
        }

        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder()
                .questionId(null)
                .writerUserId(null)
                .content(stringBuilder.toString())
                .parentId(1L)
                .build();

        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("question의 id는 null이 될 수 없습니다.");
        errorMessages.add("writer user의 id는 null이 될 수 없습니다.");
        errorMessages.add("content의 size는 1000을 넘을 수 없습니다.");

        // when
        Set<ConstraintViolation<CommentSaveRequestDto>> constraintViolations = validator.validate(requestDto);
        Iterator<ConstraintViolation<CommentSaveRequestDto>> iterator = constraintViolations.iterator();

        // then

        assertAll(
                () -> assertEquals(constraintViolations.size(), 3),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage())),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage())),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage()))
        );
    }
}