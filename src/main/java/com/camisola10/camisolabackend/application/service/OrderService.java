package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.CreateOrderUseCase;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.camisola10.camisolabackend.domain.order.Order.Status.PROCESSING;

@Service
class OrderService implements CreateOrderUseCase {

    @Override
    public OrderId createOrder(CreateOrderCommand command) {

        var order = Order.builder()
                .id(OrderId.create())
                .items(command.getItems())
                .shippingAddress(command.getShippingAddress())
                .status(PROCESSING)
                .createdAt(LocalDateTime.now())
                .build();

        return order.getId();
    }
}
