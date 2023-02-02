package com.monkeys.challenge.admin.application.services.create;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.admin.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

        // When
        userCreator.create(email, username, password);

        // Then
        verify(userRepository).createUser(email, username, password);
    }
}