package com.monkeys.challenge.domain;

import java.util.List;

public interface CustomerRepository {
    Customer save(Customer customer);
    List<Customer> findAll();
    void delete(String id);
}
