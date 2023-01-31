package com.monkeys.challenge.application.services.delete;

import com.monkeys.challenge.domain.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class CustomerRemover {

    private final CustomerRepository customerRepository;

    public void remove(String id) {
        log.debug("Removing customer with id: {}", id);
        customerRepository.delete(id);
    }
}
