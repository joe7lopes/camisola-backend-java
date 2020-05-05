package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private CommandMapper mapper;
    @Mock
    private ProductDB db;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = new ProductService(mapper, db);
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

    @Test
    public void shouldSaveProduct() {
        var command = mock(CreateProductCommand.class);
        var productMock = mock(Product.class);
        when(mapper.map(command)).thenReturn(productMock);

        productService.createProduct(command);

        verify(db).save(productMock);
        verifyNoMoreInteractions(db);
    }
}
