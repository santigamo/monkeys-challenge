package com.monkeys.challenge.customer.application.services.delete;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.domain.Customer;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerRemoverTest extends BaseTest {
    private CustomerRepository customerRepository;
    private CustomerRemover customerRemover;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerRemover = new CustomerRemover(customerRepository);

        customer = Customer.builder()
                .id(UUID.fromString(CUSTOMER_ID))
                .name(CUSTOMER_NAME)
                .surname(CUSTOMER_SURNAME)
                .avatar(CUSTOMER_AVATAR)
                .createdBy(CREATOR_USER)
                .build();
    }

    @Test
    void should_remove_a_existent_customer() {
        // given
        givenASavedCustomer();

        // when
        customerRemover.remove(CUSTOMER_ID);

        //then
        thenTheCustomerShouldBeRemoved();
    }

    private void thenTheCustomerShouldBeRemoved() {
        verify(customerRepository)
                .delete(CUSTOMER_ID);
    }

    private void givenASavedCustomer() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));
    }
}