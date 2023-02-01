package com.monkeys.challenge.admin.application.services.login;

import com.monkeys.challenge.admin.domain.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class LoginService {

    private final UserRepository userRepository;

    public String login(String username, String password) {
        log.debug("Logging in with user {}", username);
        return userRepository.doLoginWithPassword(username, password);
    }
}
