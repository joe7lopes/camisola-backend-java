package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;

public interface OrderCommandService {

    OrderId createOrder(CreateOrderCommand command);
}
