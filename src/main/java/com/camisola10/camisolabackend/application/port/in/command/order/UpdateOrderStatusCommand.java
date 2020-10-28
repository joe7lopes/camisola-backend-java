package com.camisola10.camisolabackend.application.port.in.command.order;

import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@AllArgsConstructor
@Builder
public class UpdateOrderStatusCommand {
    OrderId orderId;
    Order.Status status;
}
