package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.FetchOrdersCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.order.Email;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
interface OrderRequestMapper {

    CreateOrderCommand map(CreateOrderRequest dto);

    UpdateOrderStatusCommand map(String orderId, UpdateOrderStatusRequest request);

    default FetchOrdersCommand mapStatus(String status) {
        return new FetchOrdersCommand(Order.Status.valueOf(status.toUpperCase()));
    }

    default FetchOrdersResponse map(List<Order> orders) {
        List<FetchOrdersResponse.OrderDto> ordersDto = orders.stream()
                .map(this::map)
                .collect(Collectors.toList());
        return new FetchOrdersResponse(ordersDto);
    }

    @Mapping(target = "total", source = "total")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd/MM/yyyy HH:mm")
    FetchOrdersResponse.OrderDto map(Order order);

    @Mapping(target = "size", source = "size.size.value")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    FetchOrdersResponse.OrderItemDto map(OrderItem orderItem);

    default Email map(String email) {
        return new Email(email);
    }

    default String mapEmail(Email email) {
        return email.asString();
    }

    default OrderId mapOrderId(String orderId) {
        return OrderId.from(orderId);
    }

    default String mapProductId(Product.ProductId productId) {
        return productId.asString();
    }

    default String mapMoney(Money money) {
        return money.asString();
    }

    default String map(OrderId orderId) {
        return orderId.asString();
    }

}
