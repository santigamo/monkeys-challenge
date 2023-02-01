package com.monkeys.challenge.admin.infrastructure.exceptions;


import com.monkeys.challenge.admin.domain.exceptions.GenericError;
import com.monkeys.challenge.admin.domain.exceptions.UserAuthenticationException;
import com.monkeys.challenge.admin.domain.exceptions.UserCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class AdminControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<Object> handleUserAuthenticationException(UserAuthenticationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Object> handleUserCreationException(UserCreationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "User creation failed");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GenericError.class)
    public ResponseEntity<Object> handleGenericError() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Unexpected API error");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
