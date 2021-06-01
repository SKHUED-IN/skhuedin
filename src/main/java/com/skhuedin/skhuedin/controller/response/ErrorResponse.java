package com.skhuedin.skhuedin.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse<T> extends BasicResponse {

    private T errorMessage;
    private String errorCode;

    public ErrorResponse(T errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = "404";
    }

    public ErrorResponse(T errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}