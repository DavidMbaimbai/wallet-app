package com.davidm.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String resource, final String fieldName, final String mobileNumber) {
        super("%s not found for field %s : %s.".formatted(resource, fieldName, mobileNumber));
    }
}
