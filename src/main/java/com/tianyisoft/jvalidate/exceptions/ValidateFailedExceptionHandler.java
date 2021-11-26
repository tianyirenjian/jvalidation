package com.tianyisoft.jvalidate.exceptions;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Component
@ConditionalOnMissingBean(name = "validateFailedExceptionHandler")
@RestControllerAdvice
public class ValidateFailedExceptionHandler {
    @ExceptionHandler(ValidateFailedException.class)
    public ResponseEntity<?> validateFailed(ValidateFailedException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "The given data was invalid.");
        map.put("errors", exception.getReason());
        return ResponseEntity.status(exception.getStatus())
                .header("content-type", "application/json;charset=utf-8")
                .body(map);
    }
}
