package com.monkeys.challenge.admin.application.services.delete;

import com.monkeys.challenge.admin.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserRemoverTest {

    UserRepository repository;
    UserRemover remover;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        remover = new UserRemover(repository);
    }

    @Test
    void remove() {
        // Given
        String userId = "1234";

        // When
        remover.remove(userId);

        // Then
        verify(repository).delete(userId);
    }
}