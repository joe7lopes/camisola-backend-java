package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
interface ProductRequestMapper {

    @Mapping(target="customizable" , source="isCustomizable")
    CreateProductCommand map(CreateProductRequest request);

    ProductResponseDto map(Product product);

    @Mapping(target = "productId", source = "id")
    RemoveProductCommand map(String id);

    default List<ProductSize> toProductSize(List<ProductSizeDto> sizes) {
        return sizes.stream().map(s -> new ProductSize(new Size(s.getSize()), new Money(new BigDecimal(s.getPrice()))))
                .collect(Collectors.toList());
    }

    default List<ProductCategory> toProductCategory(List<String> categories) {
        return categories.stream().map(ProductCategory::new)
                .collect(Collectors.toList());
    }

    default ProductSizeDto map(ProductSize size){
        return new ProductSizeDto(size.getSize().getValue(), size.getPrice().asString());
    }


    default Money toMoney(String amount){
        return Money.from(amount);
    }

    default String fromMoney(Money money){
        return money.getValue().toPlainString();
    }

    default String fromProductId(Product.ProductId productId){
        return productId.getValue().toString();
    }

    default Product.ProductId toProductId(String id){
        return new Product.ProductId(UUID.fromString(id));
    }
}
