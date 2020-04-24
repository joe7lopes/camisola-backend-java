package com.camisola10.camisolabackend.domain.product;

import lombok.Value;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Value
public class ProductCategory {
    String name;
    String displayName;

    public ProductCategory(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
        validate();
    }

    private void validate() {
        if (isBlank(name)) {
            throw new InvalidCategoryNameException("Category name cannot be empty");
        }
    }

    public static class InvalidCategoryNameException extends RuntimeException {
        public InvalidCategoryNameException(String message) {
            super(message);
        }
    }
}
