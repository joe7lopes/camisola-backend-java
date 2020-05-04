package com.camisola10.camisolabackend.adapter.persistence.product;

import com.camisola10.camisolabackend.domain.product.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
interface ProductDBMapper {

    @Mapping(target= "productId", source= "product.id.value")
    @Mapping(target= "defaultPrice", source= "product.defaultPrice.value")
    ProductDb map(Product product);

    Product map(ProductDb productDb);

    default Product.ProductId convert(String productId){
        return new Product.ProductId(productId);
    }

    default Money convert(BigDecimal defaultPrice){
        return new Money(defaultPrice);
    }
}
