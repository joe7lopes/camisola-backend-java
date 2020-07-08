package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.domain.product.Product;
import org.springframework.stereotype.Component;

@Component
class CommandMapper {

    Product map(CreateProductCommand command) {
        return Product.createWithId(
                command.getName(),
                command.getCategories(),
                command.getSizes(),
                null,
                command.isCustomizable(),
                command.getDefaultPrice()
        );
    }
}
