package com.monkeys.challenge.customer.infrastructure.persistence;

import com.monkeys.challenge.admin.domain.exceptions.GenericError;
import com.monkeys.challenge.customer.domain.Customer;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.domain.exceptions.CustomerAlreadyExistsException;
import com.monkeys.challenge.customer.domain.exceptions.CustomerNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * PostgresSQL implementation of {@link CustomerRepository}
 * @author Santi
 */
@AllArgsConstructor
@Log4j2
public class PostgresCustomerRepository implements CustomerRepository {

    /**
     * Support for named parameters in JDBC
     */
    private NamedParameterJdbcTemplate jdbcTemplate;

   /**
    * {@inheritDoc}
    */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(String id, String name, String surname, String avatar, String updatedBy) {
        //? Update customer
        StringBuilder queryBuilder = new StringBuilder("UPDATE customer SET updatedBy = :updatedBy");

        var parameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("updatedBy", updatedBy);

        //? Add optionals parameters to query

        if (StringUtils.hasText(name)) {
            queryBuilder.append(", name = :name");
            parameters.addValue("name", name);
        }

        if (StringUtils.hasText(surname)) {
            queryBuilder.append(", surname = :surname");
            parameters.addValue("surname", surname);
        }

        if (StringUtils.hasText(avatar)) {
            queryBuilder.append(", avatar = :avatar");
            parameters.addValue("avatar", avatar);
        }

        //? Add where clause
        queryBuilder.append(" WHERE id = :id");

        //? Execute query
        try {
            jdbcTemplate.update(
                    queryBuilder.toString(), parameters
            );
        }
        catch (Exception e) {
            log.error("Error Updating customer {}", id);
            throw new GenericError();
        }

        log.debug("Updated customer with id: {}", id);
    }

    /**
     * {@inheritDoc}
     */
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
     * {@inheritDoc}
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

    /**
     * {@inheritDoc}
     */
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

    /**
     *
     */
    @Override
    public void deleteAll() {
        //? Delete all customers
        var query = "DELETE FROM customer";

        //? Execute query
        jdbcTemplate.update(
                query, new MapSqlParameterSource()
        );

        log.debug("Deleted all customers");

    }

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
