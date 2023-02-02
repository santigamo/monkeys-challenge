package com.monkeys.challenge.admin.infrastructure.rest.login;

import com.monkeys.challenge.admin.application.services.login.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostLoginControllerTest {

    private LoginService loginService;
    private PostLoginController controller;

    @BeforeEach
    void setUp() {
        loginService = mock(LoginService.class);
        controller = new PostLoginController(loginService);
    }

    @Test
    void login() {
        // Given - login request
        var request = new LoginRequest("username", "password");
        when(loginService.login(any(), any())).thenReturn("token");
        // When - the controller is called
        var response = controller.login(request);

        // Then - the response is the expected one
        assertNotNull(response.getBody());
    }
}