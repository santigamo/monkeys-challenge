package com.monkeys.challenge.admin.infrastructure.rest.find;

import com.monkeys.challenge.admin.application.services.find.UserFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetListUsersControllerTest {

    UserFinder userFinder;
    GetListUsersController controller;

    @BeforeEach
    void setUp() {
        userFinder = mock(UserFinder.class);
        controller = new GetListUsersController(userFinder);
    }

    @Test
    void should_list_users() {
        // Given - list user request
        List<User> users = new ArrayList<>();
        users.add(new User("123456","John","Doe","johnd@email.com", "today", "today"));
        var expectedResponse = new ListUsersResponse(users);
        when(userFinder.findAll()).thenReturn(expectedResponse);

        // When - the controller is called
        var actualResposne = controller.listUsers();

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(expectedResponse, HttpStatus.OK), actualResposne);
    }
}