package com.monkeys.challenge.admin.infrastructure.rest.role;

import com.monkeys.challenge.admin.application.services.role.UserRoleManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("unit-test")
class GetMakeAdminControllerTest {

    UserRoleManager userRoleManager;
    GetMakeAdminController controller;

    @BeforeEach
    void setUp() {
        userRoleManager = mock(UserRoleManager.class);
        controller = new GetMakeAdminController(userRoleManager);
    }

    @Test
    void should_ad_admin_status() {
        // Given - user role request
        var userId = "123456";
        var expectedResponse = new GetMakeAdminController.UserAdminStatus(userId,"The user is now admin");
        when(userRoleManager.changeAdminStatus(userId)).thenReturn(true);

        // When - the controller is called
        var actualResponse = controller.addRoleToUser(userId);

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(expectedResponse,HttpStatus.OK), actualResponse);
    }

    @Test
    void should_remove_admin_status() {
        // Given - user role request
        var userId = "123456";
        var expectedResponse = new GetMakeAdminController.UserAdminStatus(userId,"User is no longer an admin");
        // When - the controller is called
        var actualResponse = controller.addRoleToUser(userId);

        // Then - the response is the expected one
        assertEquals(new ResponseEntity<>(expectedResponse,HttpStatus.OK), actualResponse);
    }
}