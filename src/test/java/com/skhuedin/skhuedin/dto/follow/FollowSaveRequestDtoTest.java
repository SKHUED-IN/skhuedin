package com.skhuedin.skhuedin.dto.follow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FollowSaveRequestDtoTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("FollowSaveRequestDto를 검증하는 테스트")
    void testValidation() {

        // given
        FollowSaveRequestDto requestDto = FollowSaveRequestDto.builder()
                .toUserId(null)
                .fromUserId(null)
                .build();

        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("toUser의 id는 null이 될 수 없습니다.");
        errorMessages.add("fromUser의 id는 null이 될 수 없습니다.");

        // when
        Set<ConstraintViolation<FollowSaveRequestDto>> constraintViolations = validator.validate(requestDto);
        Iterator<ConstraintViolation<FollowSaveRequestDto>> iterator = constraintViolations.iterator();

        // then
        assertAll(
                () -> assertEquals(constraintViolations.size(), 2),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage())),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage()))
        );
    }
}