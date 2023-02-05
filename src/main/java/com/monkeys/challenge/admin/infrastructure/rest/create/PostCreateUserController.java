package com.monkeys.challenge.admin.infrastructure.rest.create;

import com.monkeys.challenge.admin.application.services.create.CreateUserRequest;
import com.monkeys.challenge.admin.application.services.create.UserCreator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Log4j2
public class PostCreateUserController {

    private final UserCreator userCreator;

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('create:users')")
    @Operation(summary = "Create a new user", description = "Create a new user with the given username, email and password", tags = {"users"})
    public ResponseEntity createUser(
            @RequestBody CreateUserRequest request
    ) {
        log.debug("Received request to create the user: {}", request.username());
        userCreator.create(request.email(), request.username(), request.password());
        return ResponseEntity.ok().build();
    }
}
