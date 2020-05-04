package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.application.port.in.CreateProductUseCase;
import com.camisola10.camisolabackend.application.port.in.RetrieveProductsUseCase;
import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @MockBean
    CreateProductUseCase createProductUseCaseMock;
    @MockBean
    RetrieveProductsUseCase retrieveProductsUseCase;
    @MockBean
    ProductRequestMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldReturnListOfProducts() throws Exception {

        var product = Product.builder()
                .id(new Product.ProductId("123"))
                .name("p1")
                .customizable(true)
                .defaultPrice(Money.from("23"))
                .build();

        when(retrieveProductsUseCase.getAll()).thenReturn(List.of(product));
        when(mapper.map(any(Product.class)))
                .thenReturn(new ProductResponseDto("123", "p1", null, null, true, null, "23"));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].name").value("p1"))
                .andExpect(jsonPath("$[0].customizable").value(true))
                .andExpect(jsonPath("$[0].defaultPrice").value("23"));

        verify(retrieveProductsUseCase).getAll();
        verify(mapper).map(any(Product.class));
    }

    @Test
    void createProduct() {
    }
}
