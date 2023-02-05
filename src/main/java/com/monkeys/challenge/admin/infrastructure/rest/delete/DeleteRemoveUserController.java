package com.monkeys.challenge.admin.infrastructure.rest.delete;

import com.monkeys.challenge.admin.application.services.delete.UserRemover;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log4j2
@RequiredArgsConstructor
public class DeleteRemoveUserController {

    private final UserRemover userRemover;

    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAuthority('delete:users')")
    @Operation(summary = "Remove a user", tags = {"users"}, responses = {
            @ApiResponse(responseCode = "204", description = "User removed")
    })
    public ResponseEntity removeUser(
            @PathVariable String id
    ) {
        log.debug("Received request to remove the user with id: {}", id);
        userRemover.remove(id);
        return ResponseEntity.noContent().build();
    }
}
