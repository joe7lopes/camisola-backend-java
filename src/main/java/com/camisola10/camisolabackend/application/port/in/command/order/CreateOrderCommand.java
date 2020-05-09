package com.camisola10.camisolabackend.application.port.in.command.order;

import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderCommand {
    private List<OrderItem> items;
    private ShippingAddress shippingAddress;
}
