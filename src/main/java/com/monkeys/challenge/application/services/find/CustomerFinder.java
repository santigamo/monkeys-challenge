package com.monkeys.challenge.application.services.find;

import com.monkeys.challenge.domain.CustomerRepository;
import com.monkeys.challenge.infrastructure.rest.find.ListCustomersResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerFinder {

    private final CustomerRepository customerRepository;

    public ListCustomersResponse listCustomers() {
        var customers = customerRepository.findAll();
        return new ListCustomersResponse(customers);
    }
}
