package com.monkeys.challenge.customer.infrastructure.rest.find;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.application.services.find.CustomerFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetCustomerByIdControllerTest extends BaseTest {

    private CustomerFinder customerFinder;
    private GetCustomerByIdController controller;

    @BeforeEach
    void setUp() {
        customerFinder = mock(CustomerFinder.class);
        controller = new GetCustomerByIdController(customerFinder);
    }

    @Test
    void should_return_the_given_customer_by_id() {
        // Given - customer finder returns a customer
        var expectedCustomer = new CustomerFinder.CustomerDetailsDTO(UUID.fromString(CUSTOMER_ID), CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR, "today", "today");
        when(customerFinder.getById(CUSTOMER_ID)).thenReturn(expectedCustomer);

        // When - the controller is called
        var actualResponse = controller.getCustomerById(CUSTOMER_ID);

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(expectedCustomer, HttpStatus.OK), actualResponse);

    }
}