package com.monkeys.challenge.admin.infrastructure.rest.role;

import com.monkeys.challenge.admin.application.services.role.UserRoleManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
public class GetMakeAdminController {

    private final UserRoleManager userRoleManager;

    @GetMapping(value = "/users/{userId}/admin")
    @PreAuthorize("hasAuthority('update:users')")
    public ResponseEntity addRoleToUser(
            @PathVariable String userId
    ) {
        log.debug("Received request to change admin status to user {}", userId);
        userRoleManager.changeAdminStatus(userId);
        return ResponseEntity.ok().build();
    }
}
