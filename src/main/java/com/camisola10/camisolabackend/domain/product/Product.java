package com.camisola10.camisolabackend.domain.product;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Builder
@Data
public class Product {
    @Builder.Default
    private ProductId id = new ProductId(UUID.randomUUID());
    private String name;
    private List<ProductCategory> categories;
    private List<ProductSize> sizes;
    private List<ProductImage> images;
    private boolean customizable;
    private Money defaultPrice;


    private Product(ProductId id, String name, List<ProductCategory> categories, List<ProductSize> sizes, List<ProductImage> images, boolean customizable, Money defaultPrice) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.sizes = sizes;
        this.images = images;
        this.customizable = customizable;
        this.defaultPrice = defaultPrice;
        validate();
    }

//    public static class ProductBuilder {
//        public Product build() {
//            return new Product(new ProductId(UUID.randomUUID()), name, categories, sizes, images, customizable, defaultPrice);
//        }
//    }


    private void validate() {
        if (isBlank(name)) {
            throw new InvalidProductNameException("Product Name Cannot be empty");
        }

        if (isBlank(id.value.toString())) {
            throw new InvalidProductIdException("Invalid Product id");
        }

    }

    @Value
    public static class ProductId {
        UUID value;

        public String asString() {
            return value.toString();
        }
    }


    public static class InvalidProductNameException extends RuntimeException {
        public InvalidProductNameException(String message) {
            super(message);
        }
    }

    public static class InvalidProductIdException extends RuntimeException {
        public InvalidProductIdException(String message) {
            super(message);
        }
    }
}
