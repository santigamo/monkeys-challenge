package com.monkeys.challenge.customer.domain.exceptions;

public class CustomerAlreadyExistsException extends InvalidArgumentCustomerException {
    public CustomerAlreadyExistsException(String name) {
        super("Customer with name '" + name + "' already exists");
    }
}
