package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.Order.Status;

import java.util.List;

public interface OrderDB {

    List<Order> findAll();

    List<Order> findOrdersByStatus(Status status);

    void save(Order order);

    void updateOrderStatus(OrderId orderId, Status newStatus);
}
