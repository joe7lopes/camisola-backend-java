package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;

public interface RemoveProductUseCase {

    void removeProduct(RemoveProductCommand command);
}
