package com.skhuedin.skhuedin.dto.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserUpdateDtoTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("UserUpdateDto를 검증하는 테스트")
    void testValidation() {

        // given
        UserUpdateDto updateDto = UserUpdateDto.builder()
                .entranceYear("201")
                .graduationYear("2021")
                .build();

        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("entranceYear는 4자리여야 합니다.");

        // when
        Set<ConstraintViolation<UserUpdateDto>> constraintViolations = validator.validate(updateDto);
        Iterator<ConstraintViolation<UserUpdateDto>> iterator = constraintViolations.iterator();

        // then
        assertAll(
                () -> assertEquals(constraintViolations.size(), 1),
                () -> assertTrue(errorMessages.contains(iterator.next().getMessage()))
        );
    }
}