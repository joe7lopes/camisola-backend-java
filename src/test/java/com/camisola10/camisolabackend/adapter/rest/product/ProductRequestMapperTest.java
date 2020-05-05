package com.camisola10.camisolabackend.adapter.rest.product;

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

public class ProductRequestMapperTest {

    private ProductRequestMapper mapper = Mappers.getMapper(ProductRequestMapper.class);


    @Test
    public void shouldMapToDTO() {
        var sizes = List.of(
                new ProductSize(new Size("XL"), Money.from("23")),
                new ProductSize(new Size("S"), Money.from("13"))
        );

        var categories = List.of(
                new ProductCategory("benfica"),
                new ProductCategory("sporting")
        );

        var product = Product.builder()
                .name("p1")
                .sizes(sizes)
                .categories(categories)
                .customizable(true)
                .defaultPrice(Money.from("23"))
                .build();

        ProductResponseDto dto = mapper.map(product);

        assertThat(dto.getId()).isEqualTo(product.getId().getValue().toString());
        assertThat(dto.getName()).isEqualTo(product.getName());

        assertThat(dto.getSizes().get(0).getSize()).isEqualTo(product.getSizes().get(0).getSize());
        assertThat(dto.getSizes().get(0).getPrice()).isEqualTo(product.getSizes().get(0).getPrice());
        assertThat(dto.getSizes().get(1).getSize()).isEqualTo(product.getSizes().get(1).getSize());
        assertThat(dto.getSizes().get(1).getPrice()).isEqualTo(product.getSizes().get(1).getPrice());

        assertThat(dto.getCategories().get(0).getName()).isEqualTo(product.getCategories().get(0).getName());
        assertThat(dto.getCategories().get(0).getName()).isEqualTo(product.getCategories().get(0).getName());
        assertThat(dto.getCategories().get(1).getName()).isEqualTo(product.getCategories().get(1).getName());
        assertThat(dto.getCategories().get(1).getName()).isEqualTo(product.getCategories().get(1).getName());

        assertThat(dto.isCustomizable()).isEqualTo(product.isCustomizable());
        assertThat(dto.getDefaultPrice()).isEqualTo(product.getDefaultPrice().getValue().toPlainString());


    }

    @Test
    public void shouldCreateCommand() {
        List<ProductSizeDto> sizes = createSizes();
        List<String> categories = createCategories();

        var request = CreateProductRequest.builder()
                .name("p1")
                .sizes(sizes)
                .categories(categories)
                .isCustomizable(true)
                .defaultPrice("23")
                .build();

        CreateProductCommand command = mapper.toCommand(request);

        assertThat(command.getName()).isEqualTo("p1");
        assertThat(command.getSizes()).containsAll(List.of(
                new ProductSize(new Size("S"), Money.from("34")),
                new ProductSize(new Size("XL"), Money.from("36.6"))
        ));

        assertThat(command.getCategories()).containsAll(List.of(
                new ProductCategory("benfica"),
                new ProductCategory("camisolas")
        ));

        assertThat(command.getDefaultPrice()).isEqualTo(Money.from("23"));
        assertThat(command.isCustomizable()).isEqualTo(true);
    }

    private List<ProductSizeDto> createSizes() {
        return List.of(
                new ProductSizeDto("S", "34"),
                new ProductSizeDto("XL", "36.6")
        );
    }

    private List<String> createCategories() {
        return List.of("benfica", "camisolas");
    }
}
