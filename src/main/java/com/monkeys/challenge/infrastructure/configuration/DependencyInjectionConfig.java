package com.monkeys.challenge.infrastructure.configuration;

import com.monkeys.challenge.application.services.create.CustomerCreator;
import com.monkeys.challenge.application.services.delete.CustomerRemover;
import com.monkeys.challenge.application.services.find.CustomerFinder;
import com.monkeys.challenge.domain.CustomerRepository;
import com.monkeys.challenge.shared.ChallengeApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjectionConfig {
    @Bean
    CustomerCreator customerCreator(final CustomerRepository customerRepository) {
        return new CustomerCreator(customerRepository);
    }

    @Bean
    CustomerFinder customerFinder(final CustomerRepository customerRepository) {
        return new CustomerFinder(customerRepository);
    }

    @Bean
    CustomerRemover customerRemover(final CustomerRepository customerRepository) {
        return new CustomerRemover(customerRepository);
    }
}
