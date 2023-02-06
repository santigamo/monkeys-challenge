package com.monkeys.challenge.admin.application.services.login;

import com.monkeys.challenge.admin.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("unit-test")
class LoginServiceTest {

    UserRepository userRepository;
    LoginService loginService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        loginService = new LoginService(userRepository);
    }

    @Test
    void login() {
        // Given
        String username = "username";
        String password = "password";
        when(userRepository.doLoginWithPassword(username, password)).thenReturn("token");

        // When
        String token = loginService.login(username, password);

        // Then
        assertNotNull(token);
    }
}