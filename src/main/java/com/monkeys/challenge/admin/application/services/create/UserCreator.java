package com.monkeys.challenge.admin.application.services.create;

import com.monkeys.challenge.admin.domain.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Service class to create a new user.
 * @author Santi
 */
@AllArgsConstructor
@Log4j2
public class UserCreator {

    //* The user repository.
    private final UserRepository userRepository;

    //* Default role
    private static final String DEFAULT_ROLE = "rol_ztujh4l4vQeaa7pZ";

    /**
     * Creates a new user.
     * @param email The user's email.
     * @param username The user's username.
     * @param password The user's password.
     */
    public void create(String email, String username, String password) {
        log.debug("Creating user {}", username);
        var userId = userRepository.createUser(email, username, password);
        userId = userId.replace("auth0|", "");
        log.debug("User {} created with id {}", username, userId);
        log.debug("assigning default role to user {}", username);
        userRepository.addRole(userId, DEFAULT_ROLE);
        log.debug("default role assigned successfully to user {}", username);
    }
}
