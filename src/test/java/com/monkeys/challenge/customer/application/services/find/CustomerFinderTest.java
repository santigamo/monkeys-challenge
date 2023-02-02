package com.monkeys.challenge.customer.application.services.find;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.domain.Customer;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.infrastructure.rest.find.ListCustomersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerFinderTest extends BaseTest {

    private CustomerRepository customerRepository;
    private CustomerFinder customerFinder;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerFinder = new CustomerFinder(customerRepository);

        customer = Customer.builder()
                .id(UUID.fromString(CUSTOMER_ID))
                .name(CUSTOMER_NAME)
                .surname(CUSTOMER_SURNAME)
                .avatar(CUSTOMER_AVATAR)
                .createdBy(CREATOR_USER)
                .build();
    }

    @Test
    void should_list_existing_customer() {
        // given
        givenASavedCustomer();

        // when
        var actualCustomer = executeFinder();

        // then
        thenTheCustomerShouldBeListed(actualCustomer);
    }

    @Test
    void should_list_no_customer() {
        // given no saved customers

        // when
        var actualCustomer = executeFinder();

        // then
        assertTrue(actualCustomer.customers().isEmpty());
    }

    private void thenTheCustomerShouldBeListed(ListCustomersResponse actualCustomer) {
        var expectedCustomer = new ListCustomersResponse(List.of(customer));
        assertEquals(expectedCustomer, actualCustomer);
    }

    private ListCustomersResponse executeFinder() {
        return customerFinder.listCustomers();
    }

    private void givenASavedCustomer() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));
    }
}