package com.monkeys.challenge.admin.infrastructure.rest.create;

import com.monkeys.challenge.admin.application.services.create.CreateUserRequest;
import com.monkeys.challenge.admin.application.services.create.UserCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PostCreateUserControllerTest  {

    private UserCreator userCreator;
    private PostCreateUserController controller;

    @BeforeEach
    void setUp() {
        userCreator = mock(UserCreator.class);
        controller = new PostCreateUserController(userCreator);
    }

    @Test
    void createUser() {
        // Given - user create request
        var request = new CreateUserRequest("test@email.com", "username", "password");

        // When - the controller is called
        var response = controller.createUser(request);

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(HttpStatus.OK), response);
    }
}