package com.camisola10.camisolabackend.adapter.rest.product;

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
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
interface ProductRequestMapper {

    @Mapping(target = "customizable", source = "isCustomizable")
    @Mapping(target = "images", source = "imageIds")
    CreateProductCommand map(CreateProductRequest request);

    @Mapping(target = "customizable", source = "isCustomizable")
    UpdateProductCommand map(UpdateProductRequest request);

    @Mapping(target = "imagesUrl", source = "images", qualifiedByName = "mapImageUrl")
    ProductResponseDto map(Product product);

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

    default Product.ProductId toProductId(String id) {
        return new Product.ProductId(UUID.fromString(id));
    }

    @Named("mapImageUrl")
    default List<String> mapImageUrl(List<Image> images) {
        return images.stream().map(Image::getUrl).collect(Collectors.toList());
    }
}
