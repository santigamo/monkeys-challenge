package com.monkeys.challenge.customer.infrastructure.configuration;

import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.infrastructure.persistence.PostgresCustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Database configuration.
 * @author Santi
 */
@Configuration
public class DatabaseConfig {

    //* Bean to inject the CustomerRepository.
    @Bean
    CustomerRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new PostgresCustomerRepository(jdbcTemplate);
    }
}
