package com.camisola10.camisolabackend.adapter.persistence.product;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
interface ProductDBMapper {

    @Mapping(target = "defaultPrice", source = "product.defaultPrice.value")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "id", ignore = true)
    ProductDb map(Product product);

    @Mapping(target = "id", source = "productId")
    Product map(ProductDb productDb);

    default Product.ProductId map(String productId) {
        return new Product.ProductId(UUID.fromString(productId));
    }

    default String productId(Product.ProductId productId) {
        return productId.getValue().toString();
    }

    default Money convert(BigDecimal defaultPrice) {
        return new Money(defaultPrice);
    }
}
