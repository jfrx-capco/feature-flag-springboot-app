package com.assessment.featureflags.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FeatureAlreadyEnabledException extends RuntimeException {
    public FeatureAlreadyEnabledException(String message) {
        super(message);
    }
}
