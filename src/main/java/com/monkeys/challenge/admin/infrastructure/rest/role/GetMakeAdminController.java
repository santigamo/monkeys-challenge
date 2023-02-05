package com.monkeys.challenge.admin.infrastructure.rest.role;

import com.monkeys.challenge.admin.application.services.role.UserRoleManager;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping(value = "/users/{userId}/admin", produces = "application/json")
    @PreAuthorize("hasAuthority('update:users')")
    @Operation(summary = "Change admin status of the given user", tags = {"users"})
    public ResponseEntity<UserAdminStatus> addRoleToUser(
            @PathVariable String userId
    ) {
        log.debug("Received request to change admin status to user {}", userId);
        var isAdmin = userRoleManager.changeAdminStatus(userId);

        if (isAdmin) {
            return ResponseEntity.ok(new UserAdminStatus(userId,"The user is now admin"));
        } else {
            return ResponseEntity.ok(new UserAdminStatus(userId,"User is no longer an admin"));
        }
    }

    public record UserAdminStatus(String userId, String status) {    }
}
