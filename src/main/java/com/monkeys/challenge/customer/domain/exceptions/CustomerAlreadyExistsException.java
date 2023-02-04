package com.monkeys.challenge.customer.domain.exceptions;

/**
 * Exception thrown when a customer with the same name already exists.
 */
public class CustomerAlreadyExistsException extends InvalidArgumentCustomerException {

    /**
     * Constructor.
     * @param name The name of the customer.
     */
    public CustomerAlreadyExistsException(String name) {
        super("Customer with name '" + name + "' already exists");
    }
}
