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

    private final UserRepository userRepository;

    /**
     * Creates a new user.
     * @param email The user's email.
     * @param username The user's username.
     * @param password The user's password.
     */
    public void create(String email, String username, String password) {
        log.debug("Creating user {}", username);
        userRepository.createUser(email, username, password);
    }
}
