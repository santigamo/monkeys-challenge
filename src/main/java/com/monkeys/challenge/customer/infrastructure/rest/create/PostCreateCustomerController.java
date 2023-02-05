package com.monkeys.challenge.customer.infrastructure.rest.create;

import com.monkeys.challenge.customer.application.services.create.CustomerCreator;
import com.monkeys.challenge.customer.application.services.create.CustomerCreatorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;

/**
 * Post controller for creating a customer.
 */
@RestController
@RequiredArgsConstructor
@Log4j2
public class PostCreateCustomerController {
    private final CustomerCreator customerCreate;

    @Operation(summary = "Create a customer", tags = {"customers"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = CreateCustomerResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    }
    )
    @PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('create:customers')")
    public ResponseEntity<CreateCustomerResponse> createCustomer(
            Principal principal,
            @RequestBody CustomerCreatorRequest request
    ) {
        log.debug("Received request to create the customer: {} from user: {}", request.name(), principal.getName());
        var response = customerCreate.create(request, principal.getName());
        return ResponseEntity
                .created(URI.create("/customers/%s".formatted(response)))
                .body(new CreateCustomerResponse(response));
    }

    public record CreateCustomerResponse(String customerId) {}
}
