package com.camisola10.camisolabackend.domain.product;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.images.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@NoArgsConstructor
@Builder
@Data
public class Product {
    private ProductId id;
    private String name;
    private List<ProductCategory> categories;
    @Setter(AccessLevel.NONE)
    private List<ProductSize> sizes;
    @Setter(AccessLevel.NONE)
    private List<Image> images;
    private boolean customizable;
    private Money defaultPrice;

    private Product(ProductId id, String name, List<ProductCategory> categories, List<ProductSize> sizes, List<Image> images, boolean customizable, Money defaultPrice) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.sizes = sizes;
        this.images = images;
        this.customizable = customizable;
        this.defaultPrice = defaultPrice;
        validate();
    }

    public static Product createWithId(String name, List<ProductCategory> categories, List<ProductSize> sizes, List<Image> images, boolean customizable, Money defaultPrice) {
        return new Product(
                new ProductId(UUID.randomUUID()),
                name,
                categories,
                sizes,
                images,
                customizable,
                defaultPrice
        );
    }

    public void addImage(Image image) {
        if (images == null) {
            this.images = new ArrayList<>();
        }
        this.images.add(image);
    }

    public List<Image> getImages() {
        if (images == null){
            return Collections.emptyList();
        }
        return images;
    }

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
