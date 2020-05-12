package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.domain.order.Email;
import com.camisola10.camisolabackend.domain.order.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface OrderRequestMapper {

    CreateOrderCommand map(CreateOrderRequest dto);

    UpdateOrderStatusCommand map(String orderId, UpdateOrderStatusRequest request);

    default Email map(String email) {
        return new Email(email);
    }

    default Order.OrderId mapOrderId(String orderId){
        return Order.OrderId.from(orderId);
    }
}
