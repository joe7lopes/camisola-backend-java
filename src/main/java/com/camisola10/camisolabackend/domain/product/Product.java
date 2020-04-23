package com.camisola10.camisolabackend.domain.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Product {
    @Builder.Default
    ProductId id = new ProductId(UUID.randomUUID().toString());
    String name;
    List<ProductCategory> categories;
    List<ProductSize> sizes;
    List<ProductImage> images;
    boolean isCustomizable;
    Money defaultPrice;

    @Value
    public static class ProductId {
        String value;
    }

}
