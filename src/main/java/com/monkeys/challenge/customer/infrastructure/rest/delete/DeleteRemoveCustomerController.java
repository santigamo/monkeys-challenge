package com.monkeys.challenge.customer.infrastructure.rest.delete;

import com.monkeys.challenge.customer.application.services.delete.CustomerRemover;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Log4j2
@RequiredArgsConstructor
public class DeleteRemoveCustomerController {

    private final CustomerRemover customerRemover;

    @DeleteMapping(value = "/customers/{id}")
    @PreAuthorize("hasAuthority('delete:customers')")
    @Operation(summary = "Remove a customer", tags = {"customers"}, responses = {
        @ApiResponse(responseCode = "204", description = "Customer removed"),
    })
    public ResponseEntity removeCustomer(
            Principal principal,
           @PathVariable String id
    ) {
        log.debug("Received request to remove the customer with id: {} from user: {}", id, principal.getName());
        customerRemover.remove(id);
        return ResponseEntity.noContent().build();
    }
}
