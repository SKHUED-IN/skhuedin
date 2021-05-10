package com.skhuedin.skhuedin.common.exception;

public class EmptyTokenException extends RuntimeException {

    public EmptyTokenException() {
        super();
    }

    public EmptyTokenException(String message) {
        super(message);
    }
}