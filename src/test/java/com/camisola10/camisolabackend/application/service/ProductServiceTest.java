package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    private ProductDB db;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        db = mock(ProductDB.class);
        productService = new ProductService(db);
    }

    @Test
    public void shouldRetrieveAllProducts() {
        var p1 = mock(Product.class);
        var p2 = mock(Product.class);
        var productsMock = Arrays.asList(p1, p2);
        when(db.findAll()).thenReturn(productsMock);

        List<Product> products = productService.getAll();

        assertThat(products).hasSize(2);
    }
}
