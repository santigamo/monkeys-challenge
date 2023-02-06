package com.monkeys.challenge.customer.application.services.create;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.domain.exceptions.InvalidCustomerNameException;
import com.monkeys.challenge.customer.domain.exceptions.InvalidCustomerSurnameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit-test")
class CustomerCreatorTest extends BaseTest {

    private CustomerRepository customerRepository;
    private CustomerCreator customerCreator;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerCreator = new CustomerCreator(customerRepository);
    }

    @Test
    void should_create_a_customer_successfully() {
        // Given
        givenFixedUUID(CUSTOMER_ID);
        var customer = new CustomerCreatorRequest(CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR).toDomain();

        // When
        when(customerRepository.save(any(), any()))
                .thenReturn(customer);
        customerCreator.create(new CustomerCreatorRequest(CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR), CREATOR_USER);

        // Then
        thenTheCustomerShouldBeSaved();
    }

    @Test
    void should_fail_with_invalid_name() {
        // Given
        var customer = new CustomerCreatorRequest("   ", CUSTOMER_SURNAME, CUSTOMER_AVATAR);

        // When/Then
        assertThrows(InvalidCustomerNameException.class, () -> customerCreator.create(customer, CREATOR_USER));
    }

    @Test
    void should_fail_with_invalid_surname() {
        // Given
        var customer = new CustomerCreatorRequest(CUSTOMER_NAME, "    ", CUSTOMER_AVATAR);

        // When/Then
        assertThrows(InvalidCustomerSurnameException.class, () -> customerCreator.create(customer, CREATOR_USER));
    }

    private void thenTheCustomerShouldBeSaved() {
        verify(customerRepository)
                .save(
                    any(),
                    eq(CREATOR_USER)
                );
    }
}