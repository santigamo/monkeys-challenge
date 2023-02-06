package com.monkeys.challenge.customer.domain;

import java.util.List;

public interface CustomerRepository {

    /**
     * Create a new customer
     * @param customer The customer to create
     * @param createdBy The user that created the customer
     * @return The created customer
     */
    Customer save(Customer customer, String createdBy);

    /**
     * Update a customer
     * @param id        The id of the customer to update
     * @param name      The new name of the customer
     * @param surname   The new username of the customer
     * @param avatar    The new avatar of the customer
     * @param updatedBy The user that updated the customer
     */
    void update(String id, String name, String surname , String avatar, String updatedBy);

    /**
     * Find all {@link Customer} in the database
     * @return All {@link Customer} in the database
     */
    List<Customer> findAll();

    /**
     * Find a {@link Customer} by id
     * @param id The id of the {@link Customer} to find
     * @return   The {@link Customer} with the given id
     */
    Customer findById(String id);

    /**
     * Delete a {@link Customer} by id
     * @param id The id of the {@link Customer} to delete
     */
    void delete(String id);

    /**
     * Delete all {@link Customer} in the database
     */
    void deleteAll();
}
