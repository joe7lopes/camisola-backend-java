package com.camisola10.camisolabackend.application.port.in;

import com.camisola10.camisolabackend.application.port.in.command.order.FetchOrdersCommand;
import com.camisola10.camisolabackend.domain.order.Order;

import java.util.List;

public interface FetchOrdersUseCase {

    List<Order> fetchOrders();
    List<Order> fetchOrders(FetchOrdersCommand command);
}
