package com.monkeys.challenge.admin.domain.exceptions;

public class UserCreationException extends RuntimeException {

    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserCreationException() {
        super();
    }
}
