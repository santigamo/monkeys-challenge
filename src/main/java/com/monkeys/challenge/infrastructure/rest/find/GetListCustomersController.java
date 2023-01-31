package com.monkeys.challenge.infrastructure.rest.find;

import com.monkeys.challenge.application.services.find.CustomerFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class GetListCustomersController {

    private final CustomerFinder customerFinder;

    @GetMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<ListCustomersResponse> listCustomers() {
        log.debug("Received request to list the customers");
       var response = customerFinder.listCustomers();
       return ResponseEntity.ok(response);
    }
}
