package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand.Base64Image;
import com.camisola10.camisolabackend.domain.product.ProductImage;

import java.util.List;

public interface CloudStorage {

    String store(Base64Image image);

    void removeImages(List<ProductImage> images);
}
