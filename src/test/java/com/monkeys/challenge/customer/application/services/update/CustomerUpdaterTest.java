package com.monkeys.challenge.customer.application.services.update;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Tag("unit-test")
class CustomerUpdaterTest extends BaseTest {

    private CustomerRepository customerRepository;
    private CustomerUpdater customerUpdater;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerUpdater = new CustomerUpdater(customerRepository);
    }

    @Test
    void update() {
        // Given - Update request

        // When - CustomerUpdater.update() is called
        customerUpdater.update(CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR, CREATOR_USER);

        // Then - CustomerRepository.update() should be called
        verify(customerRepository).update(CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR, CREATOR_USER);
    }
}