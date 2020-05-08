package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommandMapperTest {

    private CommandMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new CommandMapper();
    }

    @Test
    public void shouldMapFromCommandToProduct() {

        List<ProductSize> sizes = getProductSizes();
        List<ProductCategory> categories = getProductCategories();

        var command = CreateProductCommand.builder()
                .name("p1")
                .sizes(sizes)
                .categories(categories)
                .defaultPrice(Money.from("55"))
                .customizable(true)
                .build();

        Product product = mapper.map(command);

        assertThat(product.getName()).isEqualTo(command.getName());
        assertThat(product.getSizes()).containsAll(command.getSizes());
        assertThat(product.getCategories()).containsAll(command.getCategories());
        assertThat(product.getImages()).isNull();
        assertThat(product.getDefaultPrice()).isEqualTo(command.getDefaultPrice());
        assertThat(product.isCustomizable()).isEqualTo(command.isCustomizable());
    }

    private List<ProductCategory> getProductCategories() {
        return List.of(
                new ProductCategory("benfica"),
                new ProductCategory("camisolas")
        );
    }

    private List<ProductSize> getProductSizes() {
        return List.of(
                new ProductSize(new Size("S"), Money.from("34")),
                new ProductSize(new Size("XL"), Money.from("36.6"))
        );
    }

}
