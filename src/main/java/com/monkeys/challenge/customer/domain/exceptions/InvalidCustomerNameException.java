package com.monkeys.challenge.customer.domain.exceptions;

public class InvalidCustomerNameException extends InvalidArgumentCustomerException {
    public InvalidCustomerNameException(String name) {
        super("The name " + name + " is not a valid customer name");
    }
}
