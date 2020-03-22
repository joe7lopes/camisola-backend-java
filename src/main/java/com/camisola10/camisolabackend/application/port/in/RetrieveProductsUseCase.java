package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.domain.Product;

import java.util.List;

public interface RetrieveProductsUseCase {

    List<Product> getAll();
}
