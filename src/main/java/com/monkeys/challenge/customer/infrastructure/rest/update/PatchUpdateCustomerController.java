package com.monkeys.challenge.customer.infrastructure.rest.update;

import com.monkeys.challenge.customer.application.services.update.CustomerUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Log4j2
public class PatchUpdateCustomerController {

    private final CustomerUpdater customerUpdater;

    @PatchMapping(value = "/customers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:customers')")
    public ResponseEntity updateCustomer(
            Principal principal,
            @PathVariable String id,
            @RequestBody UpdateCustomerRequest request
    ) {
        log.debug("Received request to update the customer: {}", id);
        customerUpdater.update(id, request.name(), request.surname(), request.avatar(), principal.getName());
        return ResponseEntity.ok().build();
    }

    public record UpdateCustomerRequest(String name, String surname, String avatar) {}
}
