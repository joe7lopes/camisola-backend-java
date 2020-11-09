package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.Order.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDB {

    Page<Order> findAll(Pageable pageable);

    List<Order> findOrdersByStatus(Status status);

    void save(Order order);

    void updateOrderStatus(OrderId orderId, Status newStatus);
}
