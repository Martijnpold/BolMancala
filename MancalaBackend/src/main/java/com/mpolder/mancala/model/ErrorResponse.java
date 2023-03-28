package com.mpolder.mancala.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private final int code;
    private final String message;

    public ErrorResponse(HttpStatus code, String message) {
        this.code = code.value();
        this.message = message;
    }
}
