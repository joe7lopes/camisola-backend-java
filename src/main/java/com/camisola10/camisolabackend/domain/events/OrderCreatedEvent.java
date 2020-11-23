package com.camisola10.camisolabackend.domain.events;

import com.camisola10.camisolabackend.domain.order.Order;
import lombok.Value;

import java.util.UUID;

@Value
public class OrderCreatedEvent {
    UUID id = UUID.randomUUID();
    Order order;
}
