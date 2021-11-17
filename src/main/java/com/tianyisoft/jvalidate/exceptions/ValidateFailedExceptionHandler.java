package com.tianyisoft.jvalidate.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class ValidateFailedExceptionHandler {
    @ExceptionHandler(ValidateFailedException.class)
    public ResponseEntity<?> validateFailed(ValidateFailedException exception) {
        return ResponseEntity.status(exception.getStatus())
                .header("content-type", "application/json;charset=utf-8")
                .body(exception.getMessage());
    }
}
