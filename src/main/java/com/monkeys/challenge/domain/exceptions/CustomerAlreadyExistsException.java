package com.monkeys.challenge.domain.exceptions;

public class CustomerAlreadyExistsException extends InvalidArgumentCustomerException {
    public CustomerAlreadyExistsException(String message) {
        super("Customer already exists");
    }
}
