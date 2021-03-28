package com.camisola10.camisolabackend.config;

import com.camisola10.camisolabackend.application.service.ProductNotFoundException;
import com.camisola10.camisolabackend.application.service.ProductSizeNotFoundException;
import com.camisola10.camisolabackend.domain.EmailAddress.InvalidEmailAddressException;
import com.camisola10.camisolabackend.domain.order.ShippingAddress.InvalidShippingAddress;
import com.camisola10.camisolabackend.domain.product.Badge;
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
        String logRef = createUniqueId();
        return error(e, BAD_REQUEST, logRef);
    }

    @ExceptionHandler({
            Product.InvalidProductNameException.class,
            ProductCategory.InvalidCategoryNameException.class,
            ProductNotFoundException.class,
            ProductSizeNotFoundException.class,
            InvalidEmailAddressException.class,
            InvalidShippingAddress.class,
            Badge.InvalidBadgeException.class
    })
    ResponseEntity<VndErrors> handleProductExceptions(Exception e) {
        String logRef = createUniqueId();
        log.warn("invalid request {}", logRef, e);
        return error(e, BAD_REQUEST,  logRef);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<VndErrors> handleEmptyBodyRequestException(Exception ex) {
        String logRef = createUniqueId();
        log.error("Empty request body {}", logRef, ex);
        return error(ex, BAD_REQUEST, logRef);
    }

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<VndErrors> handleBadCredentialsException(Exception ex) {
        String logRef = createUniqueId();
        log.info("Wrong credentials {}",logRef, ex);
        return error(ex, UNAUTHORIZED, logRef);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    void handleUserNameNotFoundException(UsernameNotFoundException ex) {
        String id = createUniqueId();
        log.info("{} - User name not found", id, ex);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<VndErrors> handleException(Exception ex) {
        String logRef = createUniqueId();
        log.error("Server error {}", logRef, ex);
        return error(ex, INTERNAL_SERVER_ERROR, logRef);
    }

    private ResponseEntity<VndErrors> error(Exception exception, HttpStatus httpStatus, String logRef) {
        var message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
    }

    private String createUniqueId() {
        return UUID.randomUUID().toString();
    }

}

