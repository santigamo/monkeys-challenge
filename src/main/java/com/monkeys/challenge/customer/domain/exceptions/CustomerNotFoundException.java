package com.monkeys.challenge.customer.domain.exceptions;

/**
 * Exception thrown when a customer is not found.
 */
public class CustomerNotFoundException extends RuntimeException {

    /**
     * Constructor.
     * @param id Customer id.
     */
    public CustomerNotFoundException(String id) {
        super(String.format("Customer with id '%s' not found", id));
    }
}
