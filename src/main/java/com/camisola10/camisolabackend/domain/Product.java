package com.camisola10.camisolabackend.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {
    ProductId id;
    String name;
    Set<ProductCategory> categories;
    Set<ProductSize> sizes;
    boolean isCustomizable;
    Set<ProductImage> images;

    public static Product create(String name, Set<ProductCategory> categories, Set<ProductSize> sizes, boolean isCustomizable, Set<ProductImage> images) {
        var id = new ProductId(UUID.randomUUID().toString());
        return new Product(id, name, categories, sizes, isCustomizable, images);
    }

    @Value
    public static class ProductId {
        String value;
    }

}
