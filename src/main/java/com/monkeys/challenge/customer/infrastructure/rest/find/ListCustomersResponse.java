package com.monkeys.challenge.customer.infrastructure.rest.find;

import com.monkeys.challenge.customer.domain.Customer;

import java.util.List;

public record ListCustomersResponse(List<Customer> customers) {
}
