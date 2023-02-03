package com.monkeys.challenge.customer.domain;

import java.util.List;

public interface CustomerRepository {
    Customer save(Customer customer, String createdBy);
    List<Customer> findAll();

    Customer findById(String id);
    void delete(String id);
}
