package com.monkeys.challenge.application.services.create;

import com.monkeys.challenge.domain.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class CustomerCreator {

    private final CustomerRepository customerRepository;

    public CustomerCreatorResponse create(CustomerCreatorRequest request) {
        log.debug("Creating customer with name: {}", request.name());
        var savedCustomer = customerRepository.save(request.toDomain());

        log.debug("Customer created with id: {}", savedCustomer.getId());
        return new CustomerCreatorResponse(savedCustomer.getId());
    }
}
