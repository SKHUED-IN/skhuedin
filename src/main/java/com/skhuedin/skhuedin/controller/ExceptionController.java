package com.skhuedin.skhuedin.controller;

import com.skhuedin.skhuedin.controller.response.BasicResponse;
import com.skhuedin.skhuedin.controller.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    // 404
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> NotFoundException(RuntimeException e) {
        log.warn("error", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleAll(Exception e) {
        log.info(e.getClass().getName());
        log.error("error", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
    }
}