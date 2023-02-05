package com.monkeys.challenge.customer.infrastructure.rest.update;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.application.services.update.CustomerUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatchUpdateCustomerControllerTest extends BaseTest {

    private CustomerUpdater customerUpdater;
    private PatchUpdateCustomerController controller;
    Principal principal;

    @BeforeEach
    void setUp() {
        customerUpdater = mock(CustomerUpdater.class);
        controller = new PatchUpdateCustomerController(customerUpdater);
        principal = mock(Principal.class);
        when(principal.getName()).thenReturn(CREATOR_USER);
    }

    @Test
    void should_return_a_successful_response() {
        // Given - customer update request
        var request = new PatchUpdateCustomerController.UpdateCustomerRequest(CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR);
        var expectedResponse = ResponseEntity.ok().build();

        // When - the controller is called
        var actualResponse = controller.updateCustomer(principal, CUSTOMER_ID, request);

        // Then - the response is the expected one
        assertEquals(expectedResponse, actualResponse);
        verify(customerUpdater).update(CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR, CREATOR_USER);
    }
}