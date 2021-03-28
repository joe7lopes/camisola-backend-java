package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsQueryService {
    List<Product> getAll();
    Optional<Product> findProductById(Product.ProductId productId);
}
