package com.monkeys.challenge.admin.application.services.role;

import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRoleManagerTest {

    UserRepository userRepository;

    UserRoleManager userRoleManager;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userRoleManager = new UserRoleManager(userRepository);
    }

    @Test
    void changeAdminStatus_when_is_admin() {
        // Given - A userId who is an admin
        var expectedResult = false;
        var userId = "1234-5678-9012-3456";
        when(userRepository.getUserRoles(userId)).thenReturn(List.of(new UserRole("rol_5nxlqI7ChI8ICF9S", "admin", "admin role")));

        // When - Change admin status
        var actualResult = userRoleManager.changeAdminStatus(userId);

        // Then - The user is no longer admin
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void changeAdminStatus_when_is_not_admin() {
        // Given - A userId who is not an admin
        var expectedResult = true;
        var userId = "1234-5678-9012-3456";
        when(userRepository.getUserRoles(userId)).thenReturn(Collections.emptyList());

        // When - Change admin status
        var actualResult = userRoleManager.changeAdminStatus(userId);

        // Then - The user is now admin
        assertEquals(expectedResult, actualResult);
    }
}