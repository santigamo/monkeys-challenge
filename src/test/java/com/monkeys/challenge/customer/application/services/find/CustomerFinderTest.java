package com.monkeys.challenge.customer.application.services.find;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.domain.Customer;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Tag("unit-test")
class CustomerFinderTest extends BaseTest {

    private CustomerRepository customerRepository;
    private CustomerFinder customerFinder;

    private CustomerFinder.CustomerDTO customer;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerFinder = new CustomerFinder(customerRepository);

        customer = new CustomerFinder.CustomerDTO(UUID.fromString(CUSTOMER_ID), CUSTOMER_NAME);
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
        assertTrue(actualCustomer.isEmpty());
    }

    @Test
    void should_get_user_by_id() {
        // given
        givenASavedCustomer();
        var expectedCustomer = new CustomerFinder.CustomerDetailsDTO(UUID.fromString(CUSTOMER_ID), CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR, null, null);
        var domainCustomer = new Customer(CUSTOMER_ID,CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(domainCustomer);
        // when
        var actualCustomer = customerFinder.getById(CUSTOMER_ID);

        // then
        verify(customerRepository, times(1)).findById(CUSTOMER_ID);
        assertEquals(expectedCustomer, actualCustomer);
    }

    private void thenTheCustomerShouldBeListed(List<CustomerFinder.CustomerDTO> actualCustomer) {
        var expectedCustomer = List.of(customer);
        assertEquals(expectedCustomer, actualCustomer);
    }

    private List<CustomerFinder.CustomerDTO> executeFinder() {
        return customerFinder.listCustomers();
    }

    private void givenASavedCustomer() {
        Customer customer = new Customer(CUSTOMER_ID,CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR);
        when(customerRepository.findAll()).thenReturn(List.of(customer));
    }
}