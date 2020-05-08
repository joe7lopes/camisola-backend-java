package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;

public interface CreateOrderUseCase {

    void createOrder(CreateOrderCommand command);
}
