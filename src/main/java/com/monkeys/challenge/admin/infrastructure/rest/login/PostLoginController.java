package com.monkeys.challenge.admin.infrastructure.rest.login;

import com.monkeys.challenge.admin.application.services.login.LoginService;
import com.monkeys.challenge.admin.domain.exceptions.UserAuthenticationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Post controller for login.
 */
@RestController
@RequiredArgsConstructor
@Log4j2
public class PostLoginController {

    private final LoginService securityService;

    
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login", description = "Login with username and password", tags = {"login"}, responses = {
            @ApiResponse(responseCode = "200", description = "JWT Token to be used in the secured endpoints", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            })
    })
    @SecurityRequirement(name = "public")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request
    ) {
        log.debug("Received request to login with user {}", request.username());
        var token = securityService.login(request.username(), request.password());
        return ResponseEntity.ok(token);
    }
}
