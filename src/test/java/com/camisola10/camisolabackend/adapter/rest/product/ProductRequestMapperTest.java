package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.product.Money;
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
    public void shouldCreateCommand() {
        List<ProductSizeDto> sizes = createSizes();
        List<ProductCategoryDto> categories = createCategories();

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
                new ProductCategory("benfica", "Benfica"),
                new ProductCategory("camisolas", "Camisolas")
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

    private List<ProductCategoryDto> createCategories() {
        return List.of(
                new ProductCategoryDto("benfica", "Benfica"),
                new ProductCategoryDto("camisolas", "Camisolas")
        );
    }
}
