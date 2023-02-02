package com.monkeys.challenge.customer.infrastructure.rest.create;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.application.services.create.CustomerCreator;
import com.monkeys.challenge.customer.application.services.create.CustomerCreatorRequest;
import com.monkeys.challenge.customer.application.services.create.CustomerCreatorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostCreateCustomerControllerTest extends BaseTest {

    private CustomerCreator customerCreator;
    private PostCreateCustomerController controller;
    Principal principal;

    @BeforeEach
    void setUp() {
        customerCreator = mock(CustomerCreator.class);
        controller = new PostCreateCustomerController(customerCreator);

        principal = mock(Principal.class);
        when(principal.getName()).thenReturn(CREATOR_USER);
    }

    @Test
    void should_return_a_successful_response() {
        // Given - customer create request
        var request = new CustomerCreatorRequest(CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR);
        var expectedResponse = new CustomerCreatorResponse(UUID.fromString(CUSTOMER_ID));
        givenFixedUUID(CUSTOMER_ID);

        when(customerCreator.create(any(),any())).thenReturn(expectedResponse);

        // When - the controller is called
        var response = controller.createCustomer( principal, request);

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(expectedResponse, HttpStatus.OK), response);
    }
}