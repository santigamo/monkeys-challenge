package com.monkeys.challenge.customer.infrastructure.rest.find;

import com.monkeys.challenge.customer.application.services.find.CustomerFinder;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class GetListCustomersController {

    private final CustomerFinder customerFinder;

    @GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:customers')")
    @Operation(summary = "List all customers", tags = {"customers"})
    public ResponseEntity listCustomers() {
        log.debug("Received request to list the customers");
        var response = customerFinder.listCustomers();
        return ResponseEntity.ok(response);
    }
}
