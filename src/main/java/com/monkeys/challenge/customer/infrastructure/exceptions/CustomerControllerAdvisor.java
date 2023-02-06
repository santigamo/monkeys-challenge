package com.monkeys.challenge.customer.infrastructure.exceptions;

import com.monkeys.challenge.customer.domain.exceptions.CustomerAlreadyExistsException;
import com.monkeys.challenge.customer.domain.exceptions.CustomerNotFoundException;
import com.monkeys.challenge.customer.domain.exceptions.InvalidCustomerNameException;
import com.monkeys.challenge.customer.domain.exceptions.InvalidCustomerSurnameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controller advice to handle exceptions.
 * @author Santi
 */
@ControllerAdvice
public class CustomerControllerAdvisor extends ResponseEntityExceptionHandler {

    //* Handles CustomerNotFoundException.
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer not found")
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    //* Handles CustomerAlreadyExistsException.
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Customer already exists")
    public ResponseEntity<Object> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    //* Handles InvalidCustomerNameException.
    @ExceptionHandler(InvalidCustomerNameException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid customer name")
    public ResponseEntity<Object> handleInvalidCustomerNameException(InvalidCustomerNameException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    //* Handles InvalidCustomerSurnameException.
    @ExceptionHandler(InvalidCustomerSurnameException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid customer surname")
    public ResponseEntity<Object> handleInvalidCustomerSurnameException(InvalidCustomerSurnameException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
