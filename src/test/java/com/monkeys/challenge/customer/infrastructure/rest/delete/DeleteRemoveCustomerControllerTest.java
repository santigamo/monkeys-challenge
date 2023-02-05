package com.monkeys.challenge.customer.infrastructure.rest.delete;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.application.services.delete.CustomerRemover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Tag("unit-test")
class DeleteRemoveCustomerControllerTest extends BaseTest {

    private CustomerRemover customerRemover;
    private DeleteRemoveCustomerController controller;
    Principal principal;

    @BeforeEach
    void setUp() {
        customerRemover = mock(CustomerRemover.class);
        controller = new DeleteRemoveCustomerController(customerRemover);

        principal = mock(Principal.class);
        when(principal.getName()).thenReturn(CREATOR_USER);
    }

    @Test
    void should_remove_a_customer_successfully() {
        // Given - a customer id
        doNothing().when(customerRemover).remove(any());

        // When - the controller is called
        var response = controller.removeCustomer(principal, CUSTOMER_ID);

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), response);
    }
}