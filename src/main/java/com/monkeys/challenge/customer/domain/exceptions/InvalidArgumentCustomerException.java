package com.monkeys.challenge.customer.domain.exceptions;

public class InvalidArgumentCustomerException extends IllegalArgumentException {
    public InvalidArgumentCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidArgumentCustomerException(String message) {
        super(message);
    }
}
