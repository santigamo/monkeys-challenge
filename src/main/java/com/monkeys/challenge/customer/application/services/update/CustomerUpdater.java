package com.monkeys.challenge.customer.application.services.update;

import com.monkeys.challenge.customer.domain.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Service class to update a customer.
 */
@AllArgsConstructor
@Log4j2
public class CustomerUpdater {

    //* Customer repository.
    private final CustomerRepository customerRepository;

    /**
     * Updates a customer.
     * @param id Customer id.
     * @param name Customer name.
     * @param surname Customer surname.
     * @param avatar Customer avatar.
     * @param updatedBy User who updates the customer.
     */
    public void update(String id, String name, String surname, String avatar, String updatedBy) {
        log.debug("Updating customer with id: {}", id);
        updatedBy = updatedBy.replace("auth0|","");
        customerRepository.update(id, name, surname, avatar, updatedBy);
        log.debug("Customer updated with id: {}", id);
    }
}
