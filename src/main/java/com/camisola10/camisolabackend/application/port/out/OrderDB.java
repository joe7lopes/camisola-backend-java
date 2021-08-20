package com.camisola10.camisolabackend.application.port.out;

import com.camisola10.camisolabackend.application.service.FetchOrdersCriteria;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.Order.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderDB {

    Order findById(OrderId orderId);

    Page<Order> findByCriteria(FetchOrdersCriteria criteria);

    void save(Order order);

    Order updateOrder(OrderId orderId, Status newStatus, String privateNote);

    List<Order> findPrebookingOrders();
}
