package com.monkeys.challenge.customer.application.services.create;

import com.monkeys.challenge.customer.domain.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Log4j2
public class CustomerCreator {

    private final CustomerRepository customerRepository;

    public String create(CustomerCreatorRequest request, String createdBy) {
        log.debug("Creating customer with name: {}", request.name());
        createdBy = createdBy.replace("auth0|","");
        var savedCustomer = customerRepository.save(request.toDomain(), createdBy);
        log.debug("Customer created with id: {}", savedCustomer.getId());
        return savedCustomer.getId().toString();
    }
}
