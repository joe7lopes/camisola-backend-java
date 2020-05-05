package com.camisola10.camisolabackend.application.port.in.command.product;

import com.camisola10.camisolabackend.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RemoveProductCommand {
    private Product.ProductId productId;
}
