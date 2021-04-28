package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderCommand;
import com.camisola10.camisolabackend.domain.order.Order;

public interface OrderCommandService {

    Order createOrder(CreateOrderCommand command);
    void updateOrder(UpdateOrderCommand command);
}
