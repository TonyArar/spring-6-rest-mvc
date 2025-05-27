package com.spring.spring_6_rest_mvc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// from docs of @ResponseStatus:
// Marks a method or exception class with the status code and reason that should be returned.
// << by using this spring basically handles the exception for us and returns specified status code >>
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ResourceNotFoundException() {

    }

}
