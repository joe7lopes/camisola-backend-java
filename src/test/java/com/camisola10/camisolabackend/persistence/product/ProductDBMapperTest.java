package com.camisola10.camisolabackend.persistence.product;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.images.Image;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDBMapperTest {

    private ProductDBMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductDBMapper();
    }

    @Test
    void shouldMapToProductDocument() {

        var sizes = getProductSizes();
        var categories = getProductCategories();
        var images = getImages();

        Product product = Product.builder()
                .id(Product.ProductId.create())
                .name("p1")
                .categories(categories)
                .sizes(sizes)
                .images(images)
                .customizable(false)
                .defaultPrice(Money.from("12"))
                .description("description")
                .build();

        ProductDb productDb = mapper.map(product);

        assertThat(productDb.getProductId()).isEqualTo(product.getId().getValue().toString());
        assertThat(productDb.getName()).isEqualTo(product.getName());
        assertThat(productDb.isCustomizable()).isEqualTo(product.isCustomizable());
        assertThat(productDb.getDefaultPrice()).isEqualTo(product.getDefaultPrice().getValue());

        assertThat(productDb.getSizes()).containsAll(sizes);
        assertThat(productDb.getCategories()).containsAll(categories);
        assertThat(productDb.getImages()).containsAll(images);
        assertThat(productDb.getDescription()).isEqualTo(product.getDescription());
    }

    @Test
    public void shouldMapFromDBToProduct() {
        var sizes = getProductSizes();
        var categories = getProductCategories();
        var images = getImages();

        var id = UUID.randomUUID().toString();
        var productDb = ProductDb.builder()
                .productId(id)
                .name("product2")
                .sizes(sizes)
                .categories(categories)
                .images(images)
                .defaultPrice(new BigDecimal("70"))
                .customizable(true)
                .description("description")
                .build();

        var product = mapper.map(productDb);

        assertThat(product.getId().asString()).isEqualTo(productDb.getProductId());
        assertThat(product.getName()).isEqualTo(productDb.getName());
        assertThat(product.getSizes()).containsAll(productDb.getSizes());
        assertThat(product.getCategories()).containsAll(productDb.getCategories());
        assertThat(product.getImages()).containsAll(productDb.getImages());
        assertThat(product.isCustomizable()).isEqualTo(productDb.isCustomizable());
        assertThat(product.getDefaultPrice().getValue()).isEqualTo(productDb.getDefaultPrice());
        assertThat(product.getDescription()).isEqualTo(productDb.getDescription());
    }

    private List<ProductSize> getProductSizes() {
        return List.of(
                new ProductSize(ProductSize.ProductSizeId.create(), new Size("S"), Money.from("34")),
                new ProductSize(ProductSize.ProductSizeId.create(), new Size("XL"), Money.from("36.6"))
        );
    }

    private List<ProductCategory> getProductCategories() {
        return List.of(
                new ProductCategory("benfica"),
                new ProductCategory("camisolas")
        );
    }

    private List<Image> getImages() {
        var img1 = Image.builder()
                .id(Image.ImageId.create())
                .name("data1")
                .url("http://img1")
                .build();

        var img2 = Image.builder()
                .id(Image.ImageId.create())
                .name("data2")
                .url("http://img2")
                .build();
        return List.of(img1, img2);
    }
}
