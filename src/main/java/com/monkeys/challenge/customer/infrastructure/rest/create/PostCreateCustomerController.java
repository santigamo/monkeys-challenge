package com.monkeys.challenge.customer.infrastructure.rest.create;

import com.monkeys.challenge.customer.application.services.create.CustomerCreator;
import com.monkeys.challenge.customer.application.services.create.CustomerCreatorRequest;
import com.monkeys.challenge.customer.application.services.create.CustomerCreatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Post controller for creating a customer.
 */
@RestController
@RequiredArgsConstructor
@Log4j2
public class PostCreateCustomerController {
    private final CustomerCreator customerCreate;

    @PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('create:customers')")
    public ResponseEntity<CustomerCreatorResponse> createCustomer(
            Principal principal,
            @RequestBody CustomerCreatorRequest request
    ) {
        log.debug("Received request to create the customer: {} from user: {}", request.name(), principal.getName());
        var response = customerCreate.create(request, principal.getName());
        return ResponseEntity.ok(response);
    }
}
