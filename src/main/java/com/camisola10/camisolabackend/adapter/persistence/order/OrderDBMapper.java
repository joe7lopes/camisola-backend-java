package com.camisola10.camisolabackend.adapter.persistence.order;

import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
class OrderDBMapper {

    public OrderDb map(Order order) {
        return OrderDb.builder()
                .orderId(order.getId().asString())
                .items(order.getItems())
                .shippingAddress(order.getShippingAddress())
                .status(order.getStatus())
                .total(order.getTotal().getValue())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public Page<Order> map(Page<OrderDb> orderDb) {
        return orderDb.map(o -> Order.builder()
                .id(OrderId.from(o.getOrderId()))
                .items(o.getItems())
                .shippingAddress(o.getShippingAddress())
                .createdAt(o.getCreatedAt())
                .status(o.getStatus())
                .build());
    }

}
