package com.camisola10.camisolabackend.persistence.order;

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
                .privateNote(order.getPrivateNote())
                .build();
    }

    public Order map(OrderDb db) {
        return Order.builder()
                .id(OrderId.from(db.getOrderId()))
                .items(db.getItems())
                .shippingAddress(db.getShippingAddress())
                .status(db.getStatus())
                .createdAt(db.getCreatedAt())
                .privateNote(db.getPrivateNote())
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
