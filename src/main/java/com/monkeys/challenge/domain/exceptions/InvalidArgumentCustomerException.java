package com.monkeys.challenge.domain.exceptions;

public class InvalidArgumentCustomerException extends IllegalArgumentException {
    public InvalidArgumentCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidArgumentCustomerException(String message) {
        super(message);
    }
}
