package com.skhuedin.skhuedin.error.exception;

public class EntityNotFoundException extends CommonException {

    public EntityNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}