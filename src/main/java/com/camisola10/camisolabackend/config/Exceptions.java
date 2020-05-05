package com.camisola10.camisolabackend.config;

import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class Exceptions {


    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleMediaTypeNotSupported(Exception e) {
        return "Media type not supported";
    }

    @ExceptionHandler({
            Product.InvalidProductNameException.class,
            ProductCategory.InvalidCategoryNameException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleProductExceptions(Exception e) {
       log.warn("invalid product creation", e);
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    void handleException(Exception ex) {
        log.error("Server error", ex);
    }
}

