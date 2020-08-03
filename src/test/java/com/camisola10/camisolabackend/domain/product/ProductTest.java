package com.camisola10.camisolabackend.domain.product;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    public void shouldThrowExceptionForNullName() {
        Assertions.assertThrows(Product.InvalidProductNameException.class, () -> Product.builder()
                .id(new Product.ProductId(UUID.randomUUID()))
                .name(null)
                .sizes(null)
                .categories(null)
                .images(null)
                .customizable(true)
                .defaultPrice(null)
                .build());
    }

    @Test
    public void shouldThrowExceptionForEmptyName() {

        Assertions.assertThrows(Product.InvalidProductNameException.class, () -> Product.builder()
                .id(new Product.ProductId(UUID.randomUUID()))
                .name("")
                .sizes(null)
                .categories(null)
                .images(null)
                .customizable(true)
                .defaultPrice(null)
                .build());
    }

    @Test
    public void shouldValidateUsingBuilder() {
        Assertions.assertThrows(Product.InvalidProductNameException.class, () -> Product.builder().customizable(true).build());
    }

}
