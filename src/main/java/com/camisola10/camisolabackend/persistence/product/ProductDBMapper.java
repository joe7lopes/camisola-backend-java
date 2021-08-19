package com.camisola10.camisolabackend.persistence.product;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.Product.ProductId;
import org.springframework.stereotype.Component;

@Component
class ProductDBMapper {

    ProductDb map(Product product) {
        return ProductDb.builder()
                .productId(product.getId().asString())
                .name(product.getName())
                .categories(product.getCategories())
                .sizes(product.getSizes())
                .images(product.getImages())
                .badges(product.getBadges())
                .customizable(product.isCustomizable())
                .visible(product.isVisible())
                .defaultPrice(product.getDefaultPrice().getValue())
                .description(product.getDescription())
                .prebooking(product.isPrebooking())
                .build();
    }

    Product map(ProductDb productDb) {
        return Product.builder()
                .id(ProductId.from(productDb.getProductId()))
                .name(productDb.getName())
                .categories(productDb.getCategories())
                .sizes(productDb.getSizes())
                .images(productDb.getImages())
                .badges(productDb.getBadges())
                .customizable(productDb.isCustomizable())
                .visible(productDb.isVisible())
                .defaultPrice(Money.from(productDb.getDefaultPrice()))
                .description(productDb.getDescription())
                .prebooking(productDb.isPrebooking())
                .build();
    }

}
