package com.tianyisoft.jvalidate.exceptions;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

public class ValidateFailedException extends NestedRuntimeException {
     private final int status;
     private final String message;
     public ValidateFailedException(HttpStatus status, String message) {
         super(message);
         this.status = status.value();
         this.message = message;
     }

    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
