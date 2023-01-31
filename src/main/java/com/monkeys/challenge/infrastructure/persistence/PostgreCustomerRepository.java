package com.monkeys.challenge.infrastructure.persistence;

import com.monkeys.challenge.domain.Customer;
import com.monkeys.challenge.domain.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Log4j2
public class PostgreCustomerRepository implements CustomerRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Customer save(Customer customer) {
        //? Save the new customer
        var query = "INSERT INTO customer (id, name, surname, createdBy, updatedBy) VALUES (:id, :name, :surname, :createdBy, :updatedBy)";
        var parameters = new MapSqlParameterSource()
                .addValue("id", customer.getId())
                .addValue("name", customer.getName())
                .addValue("surname", customer.getSurname())
                .addValue("createdBy", "test")
                .addValue("updatedBy", "test");


        //? Execute query
        try {
            jdbcTemplate.update(
                    query, parameters
            );
        } catch (Exception e) {
            log.error("Customer {} already exists", customer.getName());
            throw e;
        }

        log.debug("Saved customer with name: {} and id: {}", customer.getName(), customer.getId());
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        //? Get all customers
        var query = "SELECT * FROM customer";

        //? Execute query
        var response = jdbcTemplate.query(
                query,
                (rs, rowNum) -> Customer.builder()
                        .id(UUID.fromString(rs.getString("id")))
                        .name(rs.getString("name"))
                        .surname(rs.getString("surname"))
                        .createdBy(rs.getString("createdBy"))
                        .updatedBy(rs.getString("updatedBy"))
                        .build(
                )
        );

        log.debug("Found {} customers", response.size());
        return response;
    }

    @Override
    public void delete(String id) {
        //? Delete customer
        var query = "DELETE FROM customer WHERE id = :id";
        var parameters = new MapSqlParameterSource()
                .addValue("id", id);

        //? Execute query
        jdbcTemplate.update(
                query, parameters
        );

        log.debug("Deleted customer with id: {}", id);
    }
}
