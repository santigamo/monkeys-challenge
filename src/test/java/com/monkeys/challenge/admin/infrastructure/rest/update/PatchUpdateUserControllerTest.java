package com.monkeys.challenge.admin.infrastructure.rest.update;

import com.monkeys.challenge.admin.application.services.update.UserUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Tag("unit-test")
class PatchUpdateUserControllerTest {

    UserUpdater userUpdater;
    PatchUpdateUserController controller;

    @BeforeEach
    void setUp() {
        userUpdater = mock(UserUpdater.class);
        controller = new PatchUpdateUserController(userUpdater);
    }

    @Test
    void updateUser() {
        // Given - user update request
        var request = new UpdateUserRequest("name", "username");

        // When - the controller is called
        var response = controller.updateUser("id", request);

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(HttpStatus.OK), response);
    }
}