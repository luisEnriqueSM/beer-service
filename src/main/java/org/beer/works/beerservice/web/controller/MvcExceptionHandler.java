package org.beer.works.beerservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List> validationErrorHandler(MethodArgumentNotValidException ex){
        List<String> errorsList = new ArrayList<>(ex.getErrorCount());

        ex.getFieldErrors().forEach(error -> errorsList.add(error.getObjectName() + "." + error.getField() + " : " + error.getDefaultMessage()));

        return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
    }
}
