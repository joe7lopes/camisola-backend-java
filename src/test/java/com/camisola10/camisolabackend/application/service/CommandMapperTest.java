package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommandMapperTest {


    private CommandMapper mapper = Mappers.getMapper(CommandMapper.class);

    @Test
    public void shouldMapProduct() {

        var sizes = List.of(
                new ProductSize(new Size("S"), Money.from("34")),
                new ProductSize(new Size("XL"), Money.from("36.6"))
        );

        var categories = List.of(
                new ProductCategory("benfica"),
                new ProductCategory("camisolas")
        );

        var command =  CreateProductCommand.builder()
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
        assertThat(product.getDefaultPrice()).isEqualTo(command.getDefaultPrice());
        assertThat(product.isCustomizable()).isEqualTo(command.isCustomizable());
    }
}
