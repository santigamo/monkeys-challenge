package com.monkeys.challenge.customer.domain.exceptions;

public class CustomerAlreadyExistsException extends InvalidArgumentCustomerException {
    public CustomerAlreadyExistsException(String message) {
        super("Customer already exists");
    }
}
