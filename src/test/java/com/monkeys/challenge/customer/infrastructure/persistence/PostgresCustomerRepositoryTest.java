package com.monkeys.challenge.customer.infrastructure.persistence;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.admin.domain.exceptions.GenericError;
import com.monkeys.challenge.customer.domain.Customer;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.domain.exceptions.CustomerAlreadyExistsException;
import com.monkeys.challenge.customer.domain.exceptions.CustomerNotFoundException;
import com.monkeys.challenge.shared.ChallengeApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = ChallengeApplication.class)
@Tag("unit-test")
class PostgresCustomerRepositoryTest extends BaseTest {


    @Autowired
    private CustomerRepository repository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(UUID.fromString(CUSTOMER_ID))
                .name(CUSTOMER_NAME)
                .surname(CUSTOMER_SURNAME)
                .avatar(CUSTOMER_AVATAR)
                .createdBy(CREATOR_USER)
                .build();
    }

    @Test
    void should_save_a_customer_successfully() {
        // Given - A customer to save
        // When - The customer is saved
        repository.save(customer, CREATOR_USER);

        // Then - The customer is saved
        var customerList = repository.findAll();
        assertFalse(customerList.isEmpty());
    }

    @Test
    void should_fail_if_customer_already_exists() {
        // Given - A existent customer
        givenExistingCustomer();

        // When - The customer is saved again
        // Then - CustomerAlreadyExistsException is thrown
        assertThrows(CustomerAlreadyExistsException.class, () -> repository.save(customer, CREATOR_USER));
    }

    @Test
    void should_list_the_existent_customers() {
        // Given - An existing customer
        givenExistingCustomer();

        // When - The customer is searched
        var customerList = repository.findAll();

        // Then - The customer is found
        assertFalse(customerList.isEmpty());
    }


    @Test
    void should_delete_a_existent_customer() {
        // Given - An existing customer
        givenExistingCustomer();

        // When - The customer is deleted
        repository.delete(CUSTOMER_ID);

        // Then - The customer is deleted
        var customerList = repository.findAll();
        assertTrue(customerList.isEmpty());
    }

    @Test
    void should_update_a_customer_successfully() {
        // Given - An existent customer
        givenExistingCustomer();

        // When - The customer is updated
        // Then - Do not throw any exception
        assertDoesNotThrow(() -> repository.update(CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR, CREATOR_USER));
    }

    @Test
    void should_fail_if_no_id_is_passed() {
        // Given - No customers saved
        givenExistingCustomer();
        // When - The customer is updated
        // Then - GenericError is thrown
        assertThrows(GenericError.class, () -> repository.update(CUSTOMER_ID, null, null, null, null));
    }

    @Test
    void should_find_customer_by_id() {
        // Given - An existing customer
        givenExistingCustomer();
        // When - The customer is searched
        var customer = repository.findById(CUSTOMER_ID);

        // Then - The customer is found
        assertNotNull(customer);
    }

    @Test
    void should_fail_finding_a_customer_that_not_exists() {
        // Given - No customers saved

        // When - The customer is searched
        // Then - CustomerNotFoundException is thrown
        assertThrows(CustomerNotFoundException.class, () -> repository.findById(CUSTOMER_ID));
    }

    private void givenExistingCustomer() {
        repository.save(customer, CREATOR_USER);
    }
}