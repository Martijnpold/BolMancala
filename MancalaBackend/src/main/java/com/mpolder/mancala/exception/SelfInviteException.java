package com.mpolder.mancala.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Users cannot invite themselves")
public class SelfInviteException extends RuntimeException {
    public SelfInviteException() {
        super();
    }

    public SelfInviteException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelfInviteException(String message) {
        super(message);
    }

    public SelfInviteException(Throwable cause) {
        super(cause);
    }
}
