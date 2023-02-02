package com.monkeys.challenge.admin.application.services.role;

import com.monkeys.challenge.admin.domain.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRoleManager {

    private final UserRepository userRepository;

    public boolean changeAdminStatus(String userId) {
        return userRepository.changeAdminStatus(userId);
    }
}
