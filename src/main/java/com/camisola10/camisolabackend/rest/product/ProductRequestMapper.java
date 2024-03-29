package com.camisola10.camisolabackend.rest.product;

import com.camisola10.camisolabackend.application.port.in.ProductsCommandService.UpdateProductCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.images.Image;
import com.camisola10.camisolabackend.domain.images.Image.ImageId;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
interface ProductRequestMapper {

    CreateProductCommand map(CreateProductRequest request);

    UpdateProductCommand map(UpdateProductRequest request);

    ProductResponse map(Product product);

    default ImageId map(String imageId){
        return ImageId.createFrom(imageId);
    }

    default ProductSize toProductSize(ProductSizeDto size) {
        return new ProductSize(
                ProductSize.ProductSizeId.create(),
                new Size(size.getSize()),
                new Money(new BigDecimal(size.getPrice())));
    }

    default ProductCategory toProductCategory(String category) {
        return new ProductCategory(category);
    }

    default String fromProductCategory(ProductCategory category) {
        return category.getName();
    }

    default ProductSizeResponseDto map(ProductSize size) {
        return new ProductSizeResponseDto(size.getId().asString(), size.getSize().getValue(), size.getPrice().asString());
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

    default ProductResponse.ProductImageResponse mapImageUrl(Image image) {
        return new ProductResponse.ProductImageResponse(image.getId().asString(), image.getName(), image.getUrl());
    }
}
