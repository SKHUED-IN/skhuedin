package com.skhuedin.skhuedin.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skhuedin.skhuedin.error.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private int status;
    private List<Error> errors;

    private ErrorResponse(ErrorCode code, List<Error> errors) {
        this.timestamp = LocalDateTime.now();
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
    }

    private ErrorResponse(final ErrorCode code) {
        this.timestamp = LocalDateTime.now();
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
        return new ErrorResponse(code, Error.of(bindingResult));
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(ErrorCode code, List<Error> errors) {
        return new ErrorResponse(code, errors);
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Error {
        private String field;
        private String value;
        private String reason;

        private Error(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<Error> of(String field, String value, String reason) {
            List<Error> fieldErrors = new ArrayList<>();
            fieldErrors.add(new Error(field, value, reason));
            return fieldErrors;
        }

        private static List<Error> of(BindingResult bindingResult) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new Error(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}