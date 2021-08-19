package com.camisola10.camisolabackend.domain.product;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.images.Image;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Value
@Builder
public class Product {
    ProductId id;
    String name;
    List<ProductCategory> categories;
    List<ProductSize> sizes;
    List<Image> images;
    List<Badge> badges;
    Money defaultPrice;
    String description;
    boolean customizable;
    boolean visible;
    boolean prebooking;

    private Product(
            ProductId id, String name,
            List<ProductCategory> categories,
            List<ProductSize> sizes,
            List<Image> images,
            List<Badge> badges,
            Money defaultPrice,
            String description,
            boolean customizable,
            boolean visible,
            boolean prebooking) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.sizes = sizes;
        this.images = images;
        this.badges = badges;
        this.customizable = customizable;
        this.visible = visible;
        this.defaultPrice = defaultPrice;
        this.description = description;
        this.prebooking = prebooking;
        validate();
    }

    private void validate() {
        if (isNull(id)) {
            throw new InvalidProductIdException("Invalid Product id");
        }

        if (isBlank(name)) {
            throw new InvalidProductNameException("Product Name Cannot be empty");
        }
    }

    @Value
    public static class ProductId {
        UUID value;

        public static ProductId create() {
            return new ProductId(UUID.randomUUID());
        }

        public static ProductId from(String productId) {
            return new ProductId(UUID.fromString(productId));
        }

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
