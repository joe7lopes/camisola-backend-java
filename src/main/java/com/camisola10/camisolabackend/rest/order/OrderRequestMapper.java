package com.camisola10.camisolabackend.rest.order;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand.OrderItemCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.FetchOrdersByStatusCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class OrderRequestMapper {

    public CreateOrderCommand map(CreateOrderRequest dto) {

        List<OrderItemCommand> orderItems = dto.getItems().stream()
                .map(this::toOrderItemCommand)
                .collect(toList());

        String emailStr = dto.getShippingAddress().getEmail();
        Email email = isBlank(emailStr) ? null : Email.from(emailStr);

        ShippingAddress shippingAddress = ShippingAddress.builder()
                .firstName(dto.getShippingAddress().getFirstName())
                .lastName(dto.getShippingAddress().getLastName())
                .email(email)
                .phone(dto.getShippingAddress().getPhone())
                .address(dto.getShippingAddress().getAddress())
                .city(dto.getShippingAddress().city)
                .postCode(dto.getShippingAddress().postCode)
                .build();
        return new CreateOrderCommand(orderItems, shippingAddress);
    }

    public UpdateOrderStatusCommand map(String orderId, UpdateOrderStatusRequest request) {
        return new UpdateOrderStatusCommand(OrderId.from(orderId), Order.Status.valueOf(request.getStatus()));
    }

    public Page<OrderDto> map(Page<Order> order) {
        return order.map(this::toOrderDto);
    }


    public FetchOrdersByStatusCommand mapStatus(String status) {
        return new FetchOrdersByStatusCommand(Order.Status.valueOf(status.toUpperCase()));
    }

    private OrderDto toOrderDto(Order order) {

        List<OrderDto.OrderItemDto> items = order.getItems().stream()
                .map(item -> new OrderDto.OrderItemDto(
                        item.getProduct().getId().asString(),
                        item.getProduct().getName(),
                        item.getSize().getSize().asString(),
                        item.getStampingName(),
                        item.getStampingNumber()
                ))
                .collect(toList());

        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return OrderDto.builder()
                .id(order.getId().asString())
                .items(items)
                .shippingAddress(toShippingAddressDto(order.getShippingAddress()))
                .status(order.getStatus().name())
                .total(order.getTotal().asString())
                .createdAt(order.getCreatedAt().format(formatter))
                .build();
    }

    private ShippingAddressDto toShippingAddressDto(ShippingAddress shippingAddress) {
        var email = shippingAddress.getEmail() == null ? null : shippingAddress.getEmail().asString();
        return ShippingAddressDto.builder()
                .firstName(shippingAddress.getFirstName())
                .lastName(shippingAddress.getLastName())
                .email(email)
                .phone(shippingAddress.getPhone())
                .address(shippingAddress.getAddress())
                .city(shippingAddress.getCity())
                .postCode(shippingAddress.getPostCode())
                .build();
    }

    private OrderItemCommand toOrderItemCommand(CreateOrderRequest.OrderItem dto) {
        return OrderItemCommand.builder()
                .productId(dto.getProductId())
                .sizeId(dto.getSizeId())
                .stampingName(dto.getStampingName())
                .stampingNumber(dto.getStampingNumber())
                .build();
    }
}
