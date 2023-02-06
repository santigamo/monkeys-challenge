package com.monkeys.challenge.admin.infrastructure.rest.delete;

import com.monkeys.challenge.admin.application.services.delete.UserRemover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Tag("unit-test")
class DeleteRemoveUserControllerTest {

    private UserRemover userRemover;
    private DeleteRemoveUserController controller;

    @BeforeEach
    void setUp() {
        userRemover = mock(UserRemover.class);
        controller = new DeleteRemoveUserController(userRemover);
    }

    @Test
    void removeUser() {
        // Given - a user id
        String userId = "123456";
        // When - the controller is called
        var response = controller.removeUser(userId);

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), response);
    }
}