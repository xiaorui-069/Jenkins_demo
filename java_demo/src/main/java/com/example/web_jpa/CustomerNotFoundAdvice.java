package com.example.web_jpa;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerNotFoundAdvice {
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseBody
    String customerNotFound(CustomerNotFoundException e) {
        return e.getMessage();
    }
}
