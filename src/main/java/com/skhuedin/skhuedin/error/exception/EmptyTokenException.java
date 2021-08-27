package com.skhuedin.skhuedin.error.exception;

public class EmptyTokenException extends CommonException {

    public EmptyTokenException() {
        super(ErrorCode.EMPTY_TOKEN);
    }
}