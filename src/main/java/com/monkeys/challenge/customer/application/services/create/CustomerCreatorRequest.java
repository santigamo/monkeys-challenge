package com.monkeys.challenge.customer.application.services.create;

import com.monkeys.challenge.customer.domain.Customer;

public record CustomerCreatorRequest(String name, String surname, String avatar) {

    public Customer toDomain() {
        return new Customer(name, surname);
    }
}