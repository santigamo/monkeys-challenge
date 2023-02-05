package com.monkeys.challenge.admin.infrastructure.exceptions;


import com.monkeys.challenge.admin.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class AdminControllerAdvisor extends ResponseEntityExceptionHandler {

    public static final String TIMESTAMP = "timestamp";
    public static final String MESSAGE = "message";



    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User authentication failed")
    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<Object> handleUserAuthenticationException(UserAuthenticationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, "User authentication failed");

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "User creation failed")
    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Object> handleUserCreationException(UserCreationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, "User creation failed");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "User remove failed")
    @ExceptionHandler(UserRemoveException.class)
    public ResponseEntity<Object> handleUserRemoveException(UserRemoveException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, "User remove failed");
        body.put("reason", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "User update failed")
    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<Object> handleUserUpdateException(UserUpdateException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, "User update failed");
        body.put("reason", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GenericError.class)
    public ResponseEntity<Object> handleGenericError() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, "Unexpected API error");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
