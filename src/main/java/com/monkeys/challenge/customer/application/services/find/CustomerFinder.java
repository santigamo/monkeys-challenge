package com.monkeys.challenge.customer.application.services.find;

import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.infrastructure.rest.find.ListCustomersResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerFinder {

    private final CustomerRepository customerRepository;

    public ListCustomersResponse listCustomers() {
        var customers = customerRepository.findAll();
        return new ListCustomersResponse(customers);
    }
}
