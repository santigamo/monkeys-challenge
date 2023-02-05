package com.monkeys.challenge.admin.infrastructure.rest.find;

import com.monkeys.challenge.admin.application.services.find.UserFinder;
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
public class GetListUsersController {

    private final UserFinder userFinder;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('read:users')")
    @Operation(summary = "List all users", tags = {"users"})
    public ResponseEntity<ListUsersResponse> listUsers() {
        log.debug("Received request to list the users");
        var response = userFinder.findAll();
        return ResponseEntity.ok(response);
    }
}
