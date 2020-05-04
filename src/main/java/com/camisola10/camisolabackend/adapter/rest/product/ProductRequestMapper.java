package com.camisola10.camisolabackend.adapter.rest.product;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
interface ProductRequestMapper {

    @Mapping(target="customizable" , source="isCustomizable")
    CreateProductCommand toCommand(CreateProductRequest request);

    ProductResponseDto map(Product product);

    default List<ProductSize> toProductSize(List<ProductSizeDto> sizes) {
        return sizes.stream().map(s -> new ProductSize(new Size(s.size), new Money(new BigDecimal(s.price))))
                .collect(Collectors.toList());
    }

    default List<ProductCategory> toProductCategory(List<String> categories) {
        return categories.stream().map(ProductCategory::new)
                .collect(Collectors.toList());
    }

    default Money toMoney(String amount){
        return Money.from(amount);
    }

    default String fromMoney(Money money){
        return money.getValue().toPlainString();
    }

    default String fromProductId(Product.ProductId productId){
        return productId.getValue();
    }
}
