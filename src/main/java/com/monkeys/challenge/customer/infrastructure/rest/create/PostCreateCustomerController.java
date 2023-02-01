package com.monkeys.challenge.customer.infrastructure.rest.create;

import com.monkeys.challenge.customer.application.services.create.CustomerCreator;
import com.monkeys.challenge.customer.application.services.create.CustomerCreatorRequest;
import com.monkeys.challenge.customer.application.services.create.CustomerCreatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Post controller for creating a customer.
 */
@RestController
@RequiredArgsConstructor
@Log4j2
public class PostCreateCustomerController {
    private final CustomerCreator customerCreateService;

    @PostMapping("/customers")
    public ResponseEntity<CustomerCreatorResponse> createCustomer(
            @RequestBody CustomerCreatorRequest request
    ) {
        log.debug("Received request to create the customer: {}", request.name());
        var response = customerCreateService.create(request);
        return ResponseEntity.ok(response);
    }
}
