package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.images.Image.ImageId;
import com.camisola10.camisolabackend.domain.product.Badge;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductCategory;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import lombok.Builder;
import lombok.Value;

import java.util.List;

public interface ProductsCommandService {

    Product createProduct(CreateProductCommand command);

    void removeProduct(RemoveProductCommand command);

    Product updateProduct(UpdateProductCommand command);

    @Value
    @Builder
    class UpdateProductCommand {
        String id;
        String name;
        List<ProductSize> sizes;
        List<ProductCategory> categories;
        List<ImageId> imageIds;
        List<Badge> badges;
        Money defaultPrice;
        boolean customizable;
        boolean visible;
        String description;
    }

}
