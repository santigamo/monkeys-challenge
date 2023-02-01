package com.monkeys.challenge.admin.infrastructure.configuration;

import com.monkeys.challenge.admin.application.services.login.LoginService;
import com.monkeys.challenge.admin.domain.SecurityRepository;
import com.monkeys.challenge.admin.infrastructure.http.HttpClientSecurityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminDependencyInjectionConfig {

    @Bean
    SecurityRepository securityRepository() {
        return new HttpClientSecurityRepository();
    }

    @Bean
    LoginService securityService(final SecurityRepository securityRepository) {
        return new LoginService(securityRepository);
    }
}
