package com.monkeys.challenge.customer.domain.exceptions;

/**
 * Exception thrown when the customer surname is not valid.
 */
public class InvalidCustomerSurnameException extends InvalidArgumentCustomerException {

    /**
     * Constructor.
     * @param surname The surname that is not valid.
     */
    public InvalidCustomerSurnameException(String surname) {
        super("The surname " + surname + " is not a valid customer surname");
    }
}
