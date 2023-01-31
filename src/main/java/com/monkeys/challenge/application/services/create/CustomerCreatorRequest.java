package com.monkeys.challenge.application.services.create;

import com.monkeys.challenge.domain.Customer;

import java.util.UUID;

public record CustomerCreatorRequest(String name, String surname, String avatar) {

    public Customer toDomain() {
        return new Customer(name, surname);
    }
}