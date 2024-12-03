package com.edu.reserva_salas.api.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class AppControllerAdvice {

    //not found exception handler
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundException(NotFoundException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> illegalStateException(IllegalStateException exception){
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        body.put("error", HttpStatus.BAD_REQUEST);
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return  new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


}
