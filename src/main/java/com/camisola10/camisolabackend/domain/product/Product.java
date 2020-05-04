package com.camisola10.camisolabackend.domain.product;

import lombok.*;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Data
public class Product {

    private ProductId id;
    private String name;
    private List<ProductCategory> categories;
    private List<ProductSize> sizes;
    private List<ProductImage> images;
    boolean customizable;
    Money defaultPrice;

    public Product(String name, List<ProductCategory> categories, List<ProductSize> sizes, List<ProductImage> images, boolean customizable, Money defaultPrice) {
        this.id = new ProductId(UUID.randomUUID().toString());
        this.name = name;
        this.categories = categories;
        this.sizes = sizes;
        this.images = images;
        this.customizable = customizable;
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
