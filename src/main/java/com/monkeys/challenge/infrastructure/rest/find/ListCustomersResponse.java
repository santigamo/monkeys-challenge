package com.monkeys.challenge.infrastructure.rest.find;

import com.monkeys.challenge.domain.Customer;

import java.util.List;

public record ListCustomersResponse(List<Customer> customers) {
}
