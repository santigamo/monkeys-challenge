package com.monkeys.challenge.customer.domain.exceptions;

/**
 * Exception thrown when the customer name is not valid.
 */
public class InvalidCustomerNameException extends InvalidArgumentCustomerException {

    /**
     * Constructor.
     * @param name The invalid name.
     */
    public InvalidCustomerNameException(String name) {
        super("The name " + name + " is not a valid customer name");
    }
}
