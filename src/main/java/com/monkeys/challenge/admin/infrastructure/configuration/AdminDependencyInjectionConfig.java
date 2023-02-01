package com.monkeys.challenge.admin.infrastructure.configuration;

import com.monkeys.challenge.admin.application.services.create.UserCreator;
import com.monkeys.challenge.admin.application.services.delete.UserRemover;
import com.monkeys.challenge.admin.application.services.find.UserFinder;
import com.monkeys.challenge.admin.application.services.login.LoginService;
import com.monkeys.challenge.admin.application.services.update.UserUpdater;
import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.infrastructure.http.HttpClientUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminDependencyInjectionConfig {

    @Bean
    UserRepository securityRepository() {
        return new HttpClientUserRepository();
    }

    @Bean
    LoginService securityService(final UserRepository userRepository) {
        return new LoginService(userRepository);
    }

    @Bean
    UserCreator userCreator(final UserRepository userRepository) {
        return new UserCreator(userRepository);
    }

    @Bean
    UserFinder userFinder(final UserRepository userRepository) {
        return new UserFinder(userRepository);
    }

    @Bean
    UserRemover userRemover(final UserRepository userRepository) {
        return new UserRemover(userRepository);
    }

    @Bean
    UserUpdater userUpdater(final UserRepository userRepository) {
        return new UserUpdater(userRepository);
    }
}
