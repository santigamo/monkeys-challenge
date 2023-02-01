package com.monkeys.challenge.customer.infrastructure.rest.delete;

import com.monkeys.challenge.customer.application.services.delete.CustomerRemover;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class DeleteRemoveCustomerController {

    private final CustomerRemover customerRemover;

    @DeleteMapping(value = "/customers/{id}", produces = "application/json")
    public ResponseEntity removeCustomer(
           @PathVariable String id
    ) {
        log.debug("Received request to remove the customer with id: {}", id);
        customerRemover.remove(id);
        return ResponseEntity.noContent().build();
    }
}
