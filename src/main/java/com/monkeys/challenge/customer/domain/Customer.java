package com.monkeys.challenge.customer.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Customer {
    private final UUID id;
    private final String name;
    private final String surname;
    private final String avatar;
    private final String createdBy;
    private final String updatedBy;

    public Customer(String name, String surname) {
        this(UUID.randomUUID(), name, surname, "test", "test", "test");
    }

    Customer(UUID id, String name, String surname, String avatar, String createdBy, String updatedBy) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.avatar = avatar;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
