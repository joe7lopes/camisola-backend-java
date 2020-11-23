package com.camisola10.camisolabackend.domain.events;

import com.camisola10.camisolabackend.domain.order.Order;
import lombok.Value;

@Value
public class OrderStatusUpdatedEvent {
    Order order;
}
