package com.monkeys.challenge.admin.application.services.create;

import com.monkeys.challenge.admin.domain.SecurityRepository;
import com.monkeys.challenge.admin.domain.exceptions.UserAuthenticationException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@AllArgsConstructor
@Log4j2
public class UserCreator {

    private final SecurityRepository securityRepository;

    public void create(String email, String username, String password) {
        log.debug("Creating user {}", username);
        securityRepository.createUser(email, username, password);
    }
}
