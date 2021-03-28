package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.product.Badge;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.Product.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductDB {

    List<Product> findAll();

    Optional<Product> findById(ProductId productId);

    void save(Product product);

    void deleteById(ProductId productId);

    void deleteUnmatchedBadges(List<Badge> badges);

}
