package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand.Base64Image;

public interface CloudStorage {

    String store(Base64Image image);
}
