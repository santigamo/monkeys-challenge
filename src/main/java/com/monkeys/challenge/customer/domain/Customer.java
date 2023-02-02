package com.monkeys.challenge.customer.domain;

import com.monkeys.challenge.customer.domain.exceptions.InvalidCustomerNameException;
import com.monkeys.challenge.customer.domain.exceptions.InvalidCustomerSurnameException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Getter
@Builder
@Log4j2
public class Customer {
    private final UUID id;
    private String name;
    private String surname;
    private final String avatar;
    private final String createdBy;
    private final String updatedBy;

    public Customer(String name, String surname, String avatar) {
        this(UUID.randomUUID(), name, surname, avatar, null, null);
    }

    Customer(UUID id, String name, String surname, String avatar, String createdBy, String updatedBy) {
        this.id = id;
        setName(name);
        setSurname(surname);
        this.avatar = avatar;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    private void setName(String name) {
        if (!StringUtils.hasText(name)) {
            log.error("Invalid customer name: \"{}\"", name);
            throw new InvalidCustomerNameException(name);
        }
        this.name = name;
    }

    private void setSurname(String surname) {
        if (!StringUtils.hasText(surname)) {
            log.error("Invalid customer surname: \"{}\"", surname);
            throw new InvalidCustomerSurnameException(surname);
        }
        this.surname = surname;
    }
}
