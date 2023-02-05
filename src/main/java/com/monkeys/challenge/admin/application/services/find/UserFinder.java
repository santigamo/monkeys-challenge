package com.monkeys.challenge.admin.application.services.find;

import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.infrastructure.rest.find.ListUsersResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserFinder {

        private final UserRepository userRepository;

        /**
         * Find all users
         *
         * @return {@link ListUsersResponse}
         */
        public ListUsersResponse findAll() {
            var users = userRepository.findAll();
            return new ListUsersResponse(users);
        }
}
