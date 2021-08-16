package com.skhuedin.skhuedin.dto.question;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QuestionSaveRequestDtoTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("QuestionSaveRequestDto를 검증하는 테스트")
    void testValidation() {

        // given
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 2001; i++) {
            stringBuilder.append(i);
        }

        QuestionSaveRequestDto requestDto = QuestionSaveRequestDto.builder()
                .targetUserId(null)
                .writerUserId(null)
                .title(null)
                .content(stringBuilder.toString())
                .build();

        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("targetUser의 id는 null이 될 수 없습니다.");
        errorMessages.add("writerUser의 id는 null이 될 수 없습니다.");
        errorMessages.add("title이 비어 있습니다.");
        errorMessages.add("title의 길이는 15를 넘을 수 없습니다.");
        errorMessages.add("cotent의 길이는 2000을 넘을 수 없습니다.");
        errorMessages.add("status는 null이 될 수 없습니다.");
        errorMessages.add("fix는 null이 될 수 없습니다.");

        // when
        Set<ConstraintViolation<QuestionSaveRequestDto>> constraintViolations = validator.validate(requestDto);
        Iterator<ConstraintViolation<QuestionSaveRequestDto>> iterator = constraintViolations.iterator();

        // then
        assertAll(
                () -> assertEquals(constraintViolations.size(), 6),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage())),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage())),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage())),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage())),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage())),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage()))
        );
    }
}