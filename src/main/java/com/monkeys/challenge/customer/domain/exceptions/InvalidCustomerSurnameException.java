package com.monkeys.challenge.customer.domain.exceptions;

public class InvalidCustomerSurnameException extends InvalidArgumentCustomerException {
    public InvalidCustomerSurnameException(String surname) {
        super("The surname " + surname + " is not a valid customer surname");
    }
}
