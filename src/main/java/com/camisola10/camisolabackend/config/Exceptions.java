package com.camisola10.camisolabackend.config;

import com.camisola10.camisolabackend.application.service.ProductNotFoundException;
import com.camisola10.camisolabackend.application.service.ProductSizeNotFoundException;
import com.camisola10.camisolabackend.domain.Email.InvalidEmailException;
import com.camisola10.camisolabackend.domain.order.ShippingAddress.InvalidShippingAddress;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
@Slf4j
public class Exceptions {

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    ResponseEntity<VndErrors> handleMediaTypeNotSupported(Exception e) {
        return error(e, BAD_REQUEST);
    }

    @ExceptionHandler({
            Product.InvalidProductNameException.class,
            ProductCategory.InvalidCategoryNameException.class,
            ProductNotFoundException.class,
            ProductSizeNotFoundException.class,
            InvalidEmailException.class,
            InvalidShippingAddress.class})
    ResponseEntity<VndErrors> handleProductExceptions(Exception e) {
        log.warn("invalid request", e);
        return error(e, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<VndErrors> handleEmptyBodyRequestException(Exception ex) {
        log.error("Empty request body ", ex);
        return error(ex, BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<VndErrors> handleBadCredentialsException(Exception ex) {
        log.info("Wrong credentials", ex);
        return error(ex, UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    void handleUserNameNotFoundException(UsernameNotFoundException ex) {
        String id = createUniqueId();
        log.info("{} - User name not found", id, ex);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<VndErrors> handleException(Exception ex) {
        log.error("Server error", ex);
        return error(ex, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<VndErrors> error(Exception exception, HttpStatus httpStatus) {
        String logref = createUniqueId();
        var message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(logref, message), httpStatus);
    }

    private String createUniqueId() {
        return UUID.randomUUID().toString();
    }


}

