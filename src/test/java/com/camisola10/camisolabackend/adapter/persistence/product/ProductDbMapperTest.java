package com.camisola10.camisolabackend.adapter.persistence.product;

import com.camisola10.camisolabackend.domain.product.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDbMapperTest {

    private final ProductDbMapper mapper = Mappers.getMapper(ProductDbMapper.class);

    @Test
    void shouldMapToProductDocument() {

        var sizes = List.of(
                new ProductSize(new Size("S"), Money.from("34")),
                new ProductSize(new Size("XL"), Money.from("36.6"))
        );

        var categories = List.of(
                new ProductCategory("benfica", "Benfica"),
                new ProductCategory("camisolas", "Camisolas")
        );

        Product product = Product.builder()
                .productId(new Product.ProductId("123"))
                .name("p1")
                .categories(categories)
                .sizes(sizes)
                .isCustomizable(false)
                .defaultPrice(Money.from("12"))
                .build();

        ProductDb productDb = mapper.map(product);

        assertThat(productDb.getProductId()).isEqualTo(product.getProductId().getValue());
        assertThat(productDb.getName()).isEqualTo(product.getName());
        assertThat(productDb.isCustomizable()).isEqualTo(product.isCustomizable());
        assertThat(productDb.getDefaultPrice()).isEqualTo(product.getDefaultPrice().getValue());

        assertThat(productDb.getSizes()).containsAll(sizes);
        assertThat(productDb.getCategories()).containsAll(categories);
    }
}
