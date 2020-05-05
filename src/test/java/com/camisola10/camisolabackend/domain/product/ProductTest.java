package com.camisola10.camisolabackend.domain.product;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    public void shouldThrowExceptionForNullName() {
        Assertions.assertThrows(Product.InvalidProductNameException.class, () -> {
            new Product(null, null, null, null, false, null);
        });
    }

    @Test
    public void shouldThrowExceptionForEmptyName() {

        Assertions.assertThrows(Product.InvalidProductNameException.class, () -> {
            new Product("", null, null, null, false, null);
        });
    }


    @Test
    public void shouldCreateProductWithUniqueID() {
        Product product = new Product("p1", null, null, null, false, null);

        assertThat(product.getId().getValue().toString()).isNotBlank();
    }

    @Test
    public void shouldValidateUsingBuilder() {
        Assertions.assertThrows(Product.InvalidProductNameException.class, () -> {
            Product.builder().customizable(true).build();
        });
    }
}
