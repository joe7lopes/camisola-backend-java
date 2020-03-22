package com.camisola10.camisolabackend.application.port.in.command.product;

import lombok.Value;

@Value
public class RemoveProductCommand {
    String productId;
}
