package com.tianyisoft.jvalidate.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

public class ValidateFailedException extends Exception {
     private final int status;
     private final Object reason;
     public ValidateFailedException(HttpStatus status, Object reason) {
         this.status = status.value();
         this.reason = reason;
     }

    public int getStatus() {
        return status;
    }

    public Object getReason() {
        return reason;
    }

    @Override
    public String getMessage() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(this.reason);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
