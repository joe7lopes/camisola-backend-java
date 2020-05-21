package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.FetchOrdersCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.domain.order.Email;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
interface OrderRequestMapper {

    CreateOrderCommand map(CreateOrderRequest dto);

    UpdateOrderStatusCommand map(String orderId, UpdateOrderStatusRequest request);

    default FetchOrdersCommand mapStatus(String status){
        return new FetchOrdersCommand(Order.Status.valueOf(status.toUpperCase()));
    };

    default FetchOrdersResponse map(List<Order> orders) {
        List<OrderDto> ordersDto = orders.stream()
                .map(this::map)
                .collect(Collectors.toList());
        return new FetchOrdersResponse(ordersDto);
    }

    OrderDto map(Order order);


    default Email map(String email) {
        return new Email(email);
    }

    default String mapEmail(Email email) {
        return email.asString();
    }

    default OrderId mapOrderId(String orderId) {
        return OrderId.from(orderId);
    }

    default String map(OrderId orderId) {
        return orderId.asString();
    }

    default OrderItemDto map(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getProduct().getId().asString(),
                orderItem.getSize().getId().asString(),
                orderItem.getStampingName(),
                orderItem.getStampingNumber()
        );
    }

}
