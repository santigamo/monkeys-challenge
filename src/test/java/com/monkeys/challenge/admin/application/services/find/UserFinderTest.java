package com.monkeys.challenge.admin.application.services.find;

import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.infrastructure.rest.find.ListUsersResponse;
import com.monkeys.challenge.admin.infrastructure.rest.find.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserFinderTest {

    UserRepository userRepository;
    UserFinder userFinder;

    User user;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userFinder = new UserFinder(userRepository);

        user = new User("1234", "Jhon", "Snow", "test@email.com", "createdAt", "lastLogin");
    }

    @Test
    void findAll() {
        // Given
        var expectedList = new ListUsersResponse(List.of(user));
        when(userRepository.findAll()).thenReturn(List.of(user));

        // When
        var actualList = userFinder.findAll();

        // Then
        assertEquals(expectedList, actualList);
    }
}