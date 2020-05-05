package com.camisola10.camisolabackend.adapter.persistence.product;

import com.camisola10.camisolabackend.domain.product.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDBMapperTest {

    private ProductDBMapper mapper = Mappers.getMapper(ProductDBMapper.class);

    @Test
    void shouldMapToProductDocument() {

        var sizes = List.of(
                new ProductSize(new Size("S"), Money.from("34")),
                new ProductSize(new Size("XL"), Money.from("36.6"))
        );

        var categories = List.of(
                new ProductCategory("benfica"),
                new ProductCategory("camisolas")
        );

        Product product = Product.builder()
                .id(new Product.ProductId("123"))
                .name("p1")
                .categories(categories)
                .sizes(sizes)
                .customizable(false)
                .defaultPrice(Money.from("12"))
                .build();

        ProductDb productDb = mapper.map(product);

        assertThat(productDb.getProductId()).isEqualTo("123");
        assertThat(productDb.getName()).isEqualTo(product.getName());
        assertThat(productDb.isCustomizable()).isEqualTo(product.isCustomizable());
        assertThat(productDb.getDefaultPrice()).isEqualTo(product.getDefaultPrice().getValue());

        assertThat(productDb.getSizes()).containsAll(sizes);
        assertThat(productDb.getCategories()).containsAll(categories);
    }
}
