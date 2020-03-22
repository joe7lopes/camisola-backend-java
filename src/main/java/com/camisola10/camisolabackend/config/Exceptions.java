package com.camisola10.camisolabackend.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Exceptions {

    @ExceptionHandler(Exception.class)
    String handleValidationException(Exception ex) {
        System.out.println("called");
        return ex.getMessage();

    }
}

