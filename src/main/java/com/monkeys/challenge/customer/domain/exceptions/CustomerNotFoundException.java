package com.monkeys.challenge.customer.domain.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String id) {
        super(String.format("Customer with id '%s' not found", id));
    }
}
