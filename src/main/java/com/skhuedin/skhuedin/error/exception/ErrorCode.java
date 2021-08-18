package com.skhuedin.skhuedin.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // common error code
    INVALID_INPUT_VALUE(400, "Invalid input value"),
    ENTITY_NOT_FOUND(400, "Entity not found"),
    ACCESS_DENIED(403, "Access is denied"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),

    // 중복된 데이터가 존재할 때
    DUPLICATE_RESOURCE(409, "Duplicate resource"),

    // token
    EMPTY_TOKEN(401, "Access token is empty");

    private final String message;
    private int status;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
}