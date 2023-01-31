package com.monkeys.challenge.infrastructure.configuration;

import com.monkeys.challenge.domain.CustomerRepository;
import com.monkeys.challenge.infrastructure.persistence.PostgreCustomerRepository;
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
