package com.monkeys.challenge.customer.infrastructure.persistence;

import com.monkeys.challenge.customer.domain.Customer;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.domain.exceptions.CustomerAlreadyExistsException;
import com.monkeys.challenge.customer.domain.exceptions.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Log4j2
public class PostgresCustomerRepository implements CustomerRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Customer save(Customer customer, String createdBy) {
        //? Save the new customer
        var query = "INSERT INTO customer (id, name, surname, avatar, createdBy, updatedBy) VALUES (:id, :name, :surname, :avatar, :createdBy, :updatedBy)";
        var parameters = new MapSqlParameterSource()
                .addValue("id", customer.getId())
                .addValue("name", customer.getName())
                .addValue("surname", customer.getSurname())
                .addValue("avatar", customer.getAvatar())
                .addValue("createdBy", createdBy)
                .addValue("updatedBy", createdBy);


        //? Execute query
        try {
            jdbcTemplate.update(
                    query, parameters
            );
        }
        catch (Exception e) {
            log.error("Customer with name \"{}\" already exists", customer.getName());
            throw new CustomerAlreadyExistsException(customer.getName());
        }

        log.debug("Saved customer with name: {} and id: {}", customer.getName(), customer.getId());
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        //? Get all customers
        var query = "SELECT * FROM customer";

        //? Execute query
        var response = jdbcTemplate.query(query,mapRow());

        log.debug("Found {} customers", response.size());
        return response;
    }

    /**
     * @param id  The id of the customer to find
     * @return   The customer with the given id
     */
    @Override
    public Customer findById(String id) {
        var query = "SELECT * FROM customer WHERE id = :id";
        var parameters = new MapSqlParameterSource().addValue("id", id);

        //? Execute query
        try{
            var response = jdbcTemplate.queryForObject(query,parameters,mapRow());
            log.debug("Found customer with id: {}", id);
            return response;
        } catch (Exception e) {
            log.error("Customer with id \"{}\" not found", id);
            throw new CustomerNotFoundException(id);
        }

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

    @NotNull
    private static RowMapper<Customer> mapRow() {
        return (rs, rowNum) -> Customer.builder()
                .id(UUID.fromString(rs.getString("id")))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .avatar(rs.getString("avatar"))
                .createdBy(rs.getString("createdBy"))
                .updatedBy(rs.getString("updatedBy"))
                .build(
                );
    }
}
