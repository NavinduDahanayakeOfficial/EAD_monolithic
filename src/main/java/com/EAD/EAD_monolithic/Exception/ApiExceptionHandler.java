package com.EAD.EAD_monolithic.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = { InsufficientItemQuantityException.class, NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException e) {

        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, badRequest);

    }
}
