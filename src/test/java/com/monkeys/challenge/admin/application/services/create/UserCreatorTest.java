package com.monkeys.challenge.admin.application.services.create;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.admin.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit-test")
class UserCreatorTest extends BaseTest {

    UserRepository userRepository;
    UserCreator userCreator;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userCreator = new UserCreator(userRepository);
    }

    @Test
    void should_create_a_user_successfully() {
        // Given
        var email = "test@email.com";
        var username = "unit-test";
        var password = "Password!";
        when(userRepository.createUser(email,username, password)).thenReturn("auth0|1234567890");

        // When
        userCreator.create(email, username, password);

        // Then

        verify(userRepository).createUser(email, username, password);
        verify(userRepository).addRole(eq("1234567890"), anyString());
    }
}