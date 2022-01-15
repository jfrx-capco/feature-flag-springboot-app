package com.assessment.featureflags.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateFeatureException extends RuntimeException {
    public DuplicateFeatureException(String message) {
        super(message);
    }
}
