package com.camisola10.camisolabackend.persistence.product;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.Product.ProductId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
interface ProductDBMapper {

    @Mapping(target = "defaultPrice", source = "product.defaultPrice.value")
    @Mapping(target = "productId", source = "product.id")
    ProductDb map(Product product);

    @Mapping(target = "id", source = "productId")
    Product map(ProductDb productDb);

    default ProductId map(String productId) {
        return new ProductId(UUID.fromString(productId));
    }

    default String productId(ProductId productId) {
        return productId.asString();
    }

    default Money convert(BigDecimal defaultPrice) {
        return new Money(defaultPrice);
    }
}
