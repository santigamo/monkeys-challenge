package com.monkeys.challenge.customer.application.services.find;

import com.monkeys.challenge.customer.domain.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.UUID;

/**
 * Service class with find customers operations.
 */
@AllArgsConstructor
@Log4j2
public class CustomerFinder {

    //* Customer repository.
    private final CustomerRepository customerRepository;

    /**
     * List all customers.
     * @return List of customers.
     */
    public List<CustomerDTO> listCustomers() {
        var customers = customerRepository.findAll();
        log.debug("Found {} customers", customers.size());

        return customers.stream()
                .map( customer -> new CustomerDTO(customer.getId(), customer.getName()))
                .toList();
    }

    /**
     * Get customer by id.
     * @param id Customer id.
     * @return Customer details.
     */
    public CustomerDetailsDTO getById(String id) {
        var customer = customerRepository.findById(id);
        log.debug("Found customer with id: {}", id);
        return new CustomerDetailsDTO(customer.getId(), customer.getName(), customer.getSurname(), customer.getAvatar(), customer.getCreatedBy(), customer.getUpdatedBy());
    }

    /**
     * Customer DTO.
     */
    public record CustomerDTO(UUID id, String name) {}

    /**
     * Customer details DTO.
     */
    public record CustomerDetailsDTO(UUID id, String name, String surname, String avatar, String createdBy, String updatedBy) {}
}
