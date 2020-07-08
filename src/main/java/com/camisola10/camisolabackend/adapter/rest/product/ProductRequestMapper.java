package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.apache.http.entity.ContentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.UUID;

@Mapper(componentModel = "spring")
interface ProductRequestMapper {

    @Mapping(target = "customizable", source = "isCustomizable")
    CreateProductCommand map(CreateProductRequest request);

    ProductResponseDto map(Product product);

    default ProductSize toProductSize(ProductSizeDto size) {
        return new ProductSize(
                ProductSize.ProductSizeId.create(),
                new Size(size.getSize()),
                new Money(new BigDecimal(size.getPrice())));
    }

    default ProductCategory toProductCategory(String categorie) {
        return new ProductCategory(categorie);
    }

    default String fromProductCategory(ProductCategory category) {
        return category.getName();
    }

    default ProductSizeResponseDto map(ProductSize size) {
        return new ProductSizeResponseDto(size.getId().asString(), size.getSize().getValue(), size.getPrice().asString());
    }

    default CreateProductCommand.Base64Image map(ProductImageDto dto) {
        var parts = dto.getFile().split("[:;,]");
        var contentType = ContentType.create(parts[1]);
        var bytes = Base64.getDecoder().decode(parts[3]);
        return new CreateProductCommand.Base64Image(dto.getName(), bytes, contentType, dto.isDefault());
    }

    default Money toMoney(String amount) {
        return Money.from(amount);
    }

    default String fromMoney(Money money) {
        return money.getValue().toPlainString();
    }

    default String fromProductId(Product.ProductId productId) {
        return productId.getValue().toString();
    }

    default Product.ProductId toProductId(String id) {
        return new Product.ProductId(UUID.fromString(id));
    }
}
