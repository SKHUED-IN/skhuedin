package com.skhuedin.skhuedin.dto.posts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SuggestionsSaveRequestDtoTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    @Test
    @DisplayName("SuggestionsSaveRequestDto를 검증하는 테스트")
    void testValidation() {
        
        // given
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5001; i++) {
            stringBuilder.append(i);
        }

        SuggestionsSaveRequestDto requestDto = SuggestionsSaveRequestDto.builder()
                .title(null)
                .content(stringBuilder.toString())
                .build();

        List<String> errorMessages = new ArrayList<>();
        errorMessages.add("title이 비어 있습니다.");
        errorMessages.add("content의 길이는 5000을 넘을 수 없습니다.");

        // when
        Set<ConstraintViolation<SuggestionsSaveRequestDto>> constraintViolations = validator.validate(requestDto);
        Iterator<ConstraintViolation<SuggestionsSaveRequestDto>> iterator = constraintViolations.iterator();

        // then
        assertAll(
                () -> assertEquals(constraintViolations.size(), 2),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage())),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage()))
        );
    }
}