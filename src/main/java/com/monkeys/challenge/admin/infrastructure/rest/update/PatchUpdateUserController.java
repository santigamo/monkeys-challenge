package com.monkeys.challenge.admin.infrastructure.rest.update;

import com.monkeys.challenge.admin.application.services.update.UserUpdater;
import com.monkeys.challenge.admin.infrastructure.rest.find.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class PatchUpdateUserController {

    private final UserUpdater userUpdater;

    @PatchMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:users')")
    public ResponseEntity<User> updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserRequest request
    ) {
        log.debug("Received request to update the user: {}", id);
        var user = userUpdater.update(id, request.name(), request.username());
        return ResponseEntity.ok(user);
    }
}
