package com.monkeys.challenge.admin.application.services.update;

import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.infrastructure.rest.find.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("unit-test")
class UserUpdaterTest {

    private UserRepository userRepository;
    private UserUpdater userUpdater;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userUpdater = new UserUpdater(userRepository);
    }

    @Test
    void update() {
        // Given
        String userId = "123456";
        String name = "John Wick";
        String username = "johnwick";
        User user = new User(userId, name, username, "", "", "");
        when(userRepository.updateUser(userId, name, username)).thenReturn(user);

        // When
        var updatedUser = userUpdater.update(userId, name, username);

        // Then
        assertNotNull(updatedUser);

    }
}