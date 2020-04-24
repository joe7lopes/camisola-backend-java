package com.camisola10.camisolabackend.domain.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Product {

    ProductId productId;
    String name;
    List<ProductCategory> categories;
    List<ProductSize> sizes;
    List<ProductImage> images;
    boolean isCustomizable;
    Money defaultPrice;


    public Product(String name, List<ProductCategory> categories, List<ProductSize> sizes, List<ProductImage> images, boolean isCustomizable, Money defaultPrice) {
        this.productId = new ProductId(UUID.randomUUID().toString());
        this.name = name;
        this.categories = categories;
        this.sizes = sizes;
        this.images = images;
        this.isCustomizable = isCustomizable;
        this.defaultPrice = defaultPrice;
        validate();
    }

    private void validate() {
        if (isBlank(name)) {
            throw new InvalidProductNameException("Product Name Cannot be empty");
        }
    }

    @Value
    public static class ProductId {
        String value;
    }


    public static class InvalidProductNameException extends RuntimeException {
        public InvalidProductNameException(String message) {
            super(message);
        }
    }
}
