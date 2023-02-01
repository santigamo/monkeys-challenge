package com.monkeys.challenge.admin.application.services.delete;

import com.monkeys.challenge.admin.domain.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class UserRemover {

    private final UserRepository userRepository;

    public void remove(String id) {
        log.debug("Removing user with id: {}", id);
        userRepository.delete(id);
    }
}
