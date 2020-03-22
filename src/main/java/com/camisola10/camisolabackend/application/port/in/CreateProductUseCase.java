package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;

public interface CreateProductUseCase {

    void createProduct(CreateProductCommand command);
}
