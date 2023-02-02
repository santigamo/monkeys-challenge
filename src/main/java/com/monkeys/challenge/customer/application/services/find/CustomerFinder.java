package com.monkeys.challenge.customer.application.services.find;

import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.infrastructure.rest.find.ListCustomersResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class CustomerFinder {

    private final CustomerRepository customerRepository;

    public ListCustomersResponse listCustomers() {
        var customers = customerRepository.findAll();
        log.debug("Found {} customers", customers.size());
        return new ListCustomersResponse(customers);
    }
}
