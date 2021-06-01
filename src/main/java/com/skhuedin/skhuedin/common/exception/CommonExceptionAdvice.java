package com.skhuedin.skhuedin.common.exception;

import com.skhuedin.skhuedin.controller.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class CommonExceptionAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class}) // 유효성 검사 실패 시 발생하는 예외를 처리
    public ResponseEntity<ErrorResponse<List<String>>> processValidationError(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>(errorMessages, "400"));
    }

    // 400
    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    public ResponseEntity<ErrorResponse<String>> BadRequestException(RuntimeException e) {
        log.warn("error", e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse<>("잘못된 정렬 조건입니다.", "400"));
    }

    // 401
    @ExceptionHandler({EmptyTokenException.class})
    public ResponseEntity<ErrorResponse<String>> EmptyTokenException(RuntimeException e) {
        log.warn("error", e);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse<>(e.getMessage(), "401"));
    }

    // 404
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse<String>> NotFoundException(RuntimeException e) {
        log.warn("error", e);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<>(e.getMessage()));
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse<String>> handleAll(Exception e) {
        log.info(e.getClass().getName());
        log.error("error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse<>(e.getMessage(), "500"));
    }
}