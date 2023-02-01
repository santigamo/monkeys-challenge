package com.monkeys.challenge.customer.infrastructure.configuration;

import com.monkeys.challenge.customer.application.services.create.CustomerCreator;
import com.monkeys.challenge.customer.application.services.delete.CustomerRemover;
import com.monkeys.challenge.customer.application.services.find.CustomerFinder;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import org.springframework.context.annotation.Bean;
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
