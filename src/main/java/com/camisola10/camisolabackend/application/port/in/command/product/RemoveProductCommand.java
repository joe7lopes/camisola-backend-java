package com.camisola10.camisolabackend.application.port.in.command.product;

import com.camisola10.camisolabackend.domain.product.Product;
import lombok.Value;

@Value
public class RemoveProductCommand {
    Product.ProductId productId;
}
