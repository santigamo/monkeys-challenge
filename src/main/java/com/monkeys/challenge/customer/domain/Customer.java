package com.monkeys.challenge.customer.domain;

import com.monkeys.challenge.customer.domain.exceptions.InvalidCustomerNameException;
import com.monkeys.challenge.customer.domain.exceptions.InvalidCustomerSurnameException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Customer domain object.
 * @author Santi
 */
@Getter
@Builder
@Log4j2
public class Customer {

    //* Customer id.
    private final UUID id;
    //* Customer name.
    private String name;
    //* Customer surname.
    private String surname;
    //* Customer avatar.
    private final String avatar;
    //* User who created the customer.
    private final String createdBy;
    //* User who updated the customer.
    private final String updatedBy;

    /**
     * Short constructor.
     * @param name Customer name.
     * @param surname Customer surname.
     * @param avatar Customer avatar url.
     */
    public Customer(String name, String surname, String avatar) {
        this(UUID.randomUUID(), name, surname, avatar, null, null);
    }

    /**
     * Full constructor.
     * @param id Customer id.
     * @param name Customer name.
     * @param surname Customer surname.
     * @param avatar Customer avatar url.
     * @param createdBy User who created the customer.
     * @param updatedBy User who updated the customer.
     */
    Customer(UUID id, String name, String surname, String avatar, String createdBy, String updatedBy) {
        this.id = id;
        setName(name);
        setSurname(surname);
        this.avatar = avatar;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    /**
     * Updates the customer name.
     * @param name Customer name.
     */
    private void setName(String name) {
        if (!StringUtils.hasText(name)) {
            log.error("Invalid customer name: \"{}\"", name);
            throw new InvalidCustomerNameException(name);
        }
        this.name = name;
    }

    /**
     * Updates the customer surname.
     * @param surname Customer surname.
     */
    private void setSurname(String surname) {
        if (!StringUtils.hasText(surname)) {
            log.error("Invalid customer surname: \"{}\"", surname);
            throw new InvalidCustomerSurnameException(surname);
        }
        this.surname = surname;
    }
}
