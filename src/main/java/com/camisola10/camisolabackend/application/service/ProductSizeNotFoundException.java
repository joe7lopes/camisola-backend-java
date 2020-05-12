package com.camisola10.camisolabackend.application.service;

public class ProductSizeNotFoundException extends RuntimeException {
    public ProductSizeNotFoundException(String message) {
        super(message);
    }
}
