package com.skhuedin.skhuedin.error.exception;

// 요구사항에 맞지 않을 경우 발생시키는 Exception
public class CommonException extends RuntimeException {

    private ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}