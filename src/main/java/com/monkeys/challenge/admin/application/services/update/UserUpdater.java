package com.monkeys.challenge.admin.application.services.update;

import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.infrastructure.rest.find.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserUpdater {

    private final UserRepository userRepository;

    /**
     * Update user
     * @param id user id
     * @param name user's name
     * @param username user's username
     * @return updated user
     */
    public User update(String id, String name, String username) {
        return userRepository.updateUser(id, name, username);
    }
}
