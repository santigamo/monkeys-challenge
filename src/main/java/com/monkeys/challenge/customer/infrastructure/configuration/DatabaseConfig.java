package com.monkeys.challenge.customer.infrastructure.configuration;

import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.infrastructure.persistence.PostgreCustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig {

    @Bean
    CustomerRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new PostgreCustomerRepository(jdbcTemplate);
    }
}
