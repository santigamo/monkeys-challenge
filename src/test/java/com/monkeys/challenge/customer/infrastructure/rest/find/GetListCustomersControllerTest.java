package com.monkeys.challenge.customer.infrastructure.rest.find;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.application.services.find.CustomerFinder;
import com.monkeys.challenge.customer.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetListCustomersControllerTest extends BaseTest {

    private CustomerFinder customerFinder;
    private GetListCustomersController controller;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customerFinder = mock(CustomerFinder.class);
        controller = new GetListCustomersController(customerFinder);

        customer = Customer.builder()
                .id(UUID.fromString(CUSTOMER_ID))
                .name(CUSTOMER_NAME)
                .surname(CUSTOMER_SURNAME)
                .avatar(CUSTOMER_AVATAR)
                .createdBy(CREATOR_USER)
                .build();
    }

    @Test
    void should_return_the_customer_list_response() {
        // Given - customer finder returns a list of customers
        var listCustomersResponse = new ListCustomersResponse(List.of(customer));
        when(customerFinder.listCustomers()).thenReturn(listCustomersResponse);

        // When - the controller is called
        var response = controller.listCustomers();

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(listCustomersResponse, HttpStatus.OK), response);
    }

    @Test
    void should_return_an_empty_list_of_customers() {
        // Given - customer finder returns an empty list of customers
        var listCustomersResponse = new ListCustomersResponse(List.of());
        when(customerFinder.listCustomers()).thenReturn(listCustomersResponse);

        // When - the controller is called
        var response = controller.listCustomers();

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(listCustomersResponse, HttpStatus.OK), response);
    }
}