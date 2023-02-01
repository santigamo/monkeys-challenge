package com.monkeys.challenge.admin.application.services.create;

import com.monkeys.challenge.admin.domain.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class UserCreator {

    private final UserRepository userRepository;

    public void create(String email, String username, String password) {
        log.debug("Creating user {}", username);
        userRepository.createUser(email, username, password);
    }
}
