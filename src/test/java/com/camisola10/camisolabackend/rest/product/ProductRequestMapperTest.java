package com.camisola10.camisolabackend.rest.product;

import com.camisola10.camisolabackend.application.port.in.ProductsCommandService.UpdateProductCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.ProductSize.ProductSizeId;
import com.camisola10.camisolabackend.domain.product.Size;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRequestMapperTest {

    private final ProductRequestMapper mapper = Mappers.getMapper(ProductRequestMapper.class);

    @Test
    public void shouldMapToDTO() {
        var sizes = List.of(
                new ProductSize(ProductSizeId.create(), new Size("XL"), Money.from("23")),
                new ProductSize(ProductSizeId.create(), new Size("S"), Money.from("13"))
        );

        var categories = List.of(
                new ProductCategory("benfica"),
                new ProductCategory("sporting")
        );

        var product = Product.builder()
                .id(Product.ProductId.create())
                .name("p1")
                .sizes(sizes)
                .categories(categories)
                .customizable(true)
                .defaultPrice(Money.from("23"))
                .visible(true)
                .prebooking(true)
                .build();

        ProductResponse dto = mapper.map(product);

        assertThat(dto.getId()).isEqualTo(product.getId().getValue().toString());
        assertThat(dto.getName()).isEqualTo(product.getName());

        assertThat(dto.getSizes().get(0).getSize()).isEqualTo(product.getSizes().get(0).getSize().getValue());
        assertThat(dto.getSizes().get(0).getPrice()).isEqualTo(product.getSizes().get(0).getPrice().asString());
        assertThat(dto.getSizes().get(1).getSize()).isEqualTo(product.getSizes().get(1).getSize().getValue());
        assertThat(dto.getSizes().get(1).getPrice()).isEqualTo(product.getSizes().get(1).getPrice().asString());

        assertThat(dto.getCategories().get(0)).isEqualTo(product.getCategories().get(0).getName());
        assertThat(dto.getCategories().get(0)).isEqualTo(product.getCategories().get(0).getName());
        assertThat(dto.getCategories().get(1)).isEqualTo(product.getCategories().get(1).getName());
        assertThat(dto.getCategories().get(1)).isEqualTo(product.getCategories().get(1).getName());

        assertThat(dto.isCustomizable()).isEqualTo(product.isCustomizable());
        assertThat(dto.isVisible()).isEqualTo(product.isVisible());
        assertThat(dto.isPrebooking()).isEqualTo(product.isPrebooking());
        assertThat(dto.getDefaultPrice()).isEqualTo(product.getDefaultPrice().getValue().toPlainString());
        assertThat(dto.getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    public void shouldMapFromDtoToCreateCommand() {
        List<ProductSizeDto> sizes = createSizes();
        List<String> categories = createCategories();
        var imageIds = List.of(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());

        var request = CreateProductRequest.builder()
                .name("p1")
                .sizes(sizes)
                .images(imageIds)
                .categories(categories)
                .defaultPrice("23")
                .description("bla bla")
                .customizable(true)
                .prebooking(true)
                .visible(true)
                .build();

        CreateProductCommand command = mapper.map(request);

        assertThat(command.getName()).isEqualTo("p1");
        assertThat(command.getSizes().get(0).getSize()).isEqualTo(new Size("S"));
        assertThat(command.getSizes().get(0).getPrice()).isEqualTo(Money.from("34"));

        assertThat(command.getSizes().get(1).getSize()).isEqualTo(new Size("XL"));
        assertThat(command.getSizes().get(1).getPrice()).isEqualTo(Money.from("36.6"));

        assertThat(command.getCategories()).containsAll(List.of(
                new ProductCategory("benfica"),
                new ProductCategory("camisolas")
        ));

        assertThat(command.getImages()).hasSize(2);
        assertThat(command.getDefaultPrice()).isEqualTo(Money.from(request.defaultPrice));
        assertThat(command.getDescription()).isEqualTo(request.description);
        assertThat(command.isCustomizable()).isEqualTo(true);
        assertThat(command.isPrebooking()).isEqualTo(true);
        assertThat(command.isVisible()).isEqualTo(true);
    }

    @Test
    public void shouldMapFromUpdateProductRequestToUpdateProductCommand() {
        var request = UpdateProductRequest.builder()
                .id(Product.ProductId.create().asString())
                .name("name")
                .categories(createCategories())
                .sizes(createSizes())
                .defaultPrice("445")
                .customizable(true)
                .visible(true)
                .prebooking(true)
                .build();

        UpdateProductCommand command = mapper.map(request);

        assertThat(command.getId()).isEqualTo(request.id);
        assertThat(command.getName()).isEqualTo(request.name);
        assertThat(command.getCategories()).hasSize(2);
        assertThat(command.getSizes()).hasSize(2);
        assertThat(command.getDefaultPrice()).isEqualTo(Money.from("445"));
        assertThat(command.isCustomizable()).isEqualTo(true);
        assertThat(command.isVisible()).isEqualTo(true);
        assertThat(command.isPrebooking()).isEqualTo(true);
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
