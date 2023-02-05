package com.monkeys.challenge.admin.application.services.role;

import com.monkeys.challenge.admin.domain.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class UserRoleManager {

    private static final String ADMIN_ROLE = "rol_5nxlqI7ChI8ICF9S";

    private final UserRepository userRepository;

    /**
     * Changes the admin status of a user
     * @param userId the user id
     * @return true if the user is now an admin, false otherwise
     */
    public boolean changeAdminStatus(String userId) {
        var roles = userRepository.getUserRoles(userId);

        var adminRole = roles.stream().filter(role -> role.id().equals(ADMIN_ROLE)).findFirst();

        if (adminRole.isPresent()) {
            userRepository.removeRole(userId, ADMIN_ROLE);
            log.debug("User {} is no longer an admin", userId);
            return false;
        } else {
            userRepository.addRole(userId, ADMIN_ROLE);
            log.debug("User {} is now an admin", userId);
            return true;
        }
    }
}
