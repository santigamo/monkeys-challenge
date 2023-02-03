package com.monkeys.challenge.customer.infrastructure.rest.find;

import com.monkeys.challenge.customer.application.services.find.CustomerFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class GetCustomerByIdController {

    private final CustomerFinder customerFinder;

    @GetMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:customers')")
    public ResponseEntity getCustomerById(@PathVariable("id") String id) {
        log.debug("Received request to get the customer with id: {}", id);
        var response = customerFinder.getById(id);
        return ResponseEntity.ok(response);
    }


}
