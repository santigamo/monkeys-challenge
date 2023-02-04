package com.monkeys.challenge.customer.domain.exceptions;

/**
 * Exception thrown when an invalid argument is passed to a method.
 */
public class InvalidArgumentCustomerException extends IllegalArgumentException {

    /**
     * Constructor.
     * @param message The message of the exception.
     */
    public InvalidArgumentCustomerException(String message) {
        super(message);
    }
}
