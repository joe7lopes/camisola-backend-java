package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.domain.product.Product;

import java.util.List;

public interface ProductsQueryService {
    List<Product> getAll();
}
