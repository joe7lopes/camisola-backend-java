package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.product.Product;

public interface CreateProductUseCase {

    Product createProduct(CreateProductCommand command);

}
