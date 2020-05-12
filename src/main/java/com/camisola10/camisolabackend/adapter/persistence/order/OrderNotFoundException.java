package com.camisola10.camisolabackend.adapter.persistence.order;

import com.camisola10.camisolabackend.domain.order.Order.OrderId;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(OrderId orderId) {
        super(String.format("Order with id %s does not exist ", orderId));
    }
}
