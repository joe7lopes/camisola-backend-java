package com.camisola10.camisolabackend.adapter.persistence.product;

import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDBMapperTest {

    private ProductDBMapper mapper = Mappers.getMapper(ProductDBMapper.class);

    @Test
    void shouldMapToProductDocument() {

        List<ProductSize> sizes = getProductSizes();
        List<ProductCategory> categories = getProductCategories();

        Product product = Product.builder()
                .name("p1")
                .categories(categories)
                .sizes(sizes)
                .customizable(false)
                .defaultPrice(Money.from("12"))
                .build();

        ProductDb productDb = mapper.map(product);

        assertThat(productDb.getProductId()).isEqualTo(product.getId().getValue().toString());
        assertThat(productDb.getName()).isEqualTo(product.getName());
        assertThat(productDb.isCustomizable()).isEqualTo(product.isCustomizable());
        assertThat(productDb.getDefaultPrice()).isEqualTo(product.getDefaultPrice().getValue());

        assertThat(productDb.getSizes()).containsAll(sizes);
        assertThat(productDb.getCategories()).containsAll(categories);
    }

    @Test
    public void shouldMapFromDBToProduct() {
        List<ProductSize> sizes = getProductSizes();
        List<ProductCategory> categories = getProductCategories();

        var id = UUID.randomUUID().toString();
        var productDb = ProductDb.builder()
                .productId(id)
                .name("product2")
                .sizes(sizes)
                .categories(categories)
                .defaultPrice(new BigDecimal("70"))
                .isCustomizable(true)
                .build();

        var product = mapper.map(productDb);

        assertThat(product.getId().asString()).isEqualTo(productDb.getProductId());
        assertThat(product.getName()).isEqualTo(productDb.getName());
        assertThat(product.getSizes()).containsAll(productDb.getSizes());
        assertThat(product.getCategories()).containsAll(productDb.getCategories());
        assertThat(product.isCustomizable()).isEqualTo(productDb.isCustomizable());
        assertThat(product.getDefaultPrice().getValue()).isEqualTo(productDb.getDefaultPrice());
    }

    private List<ProductSize> getProductSizes() {
        return List.of(
                new ProductSize(new Size("S"), Money.from("34")),
                new ProductSize(new Size("XL"), Money.from("36.6"))
        );
    }

    private List<ProductCategory> getProductCategories() {
        return List.of(
                new ProductCategory("benfica"),
                new ProductCategory("camisolas")
        );
    }
}
