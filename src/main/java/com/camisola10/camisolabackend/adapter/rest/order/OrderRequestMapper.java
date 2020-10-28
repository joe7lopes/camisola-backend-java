package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.CreateOrderCommand.OrderItemCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.FetchOrdersCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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


    public FetchOrdersResponse map(List<Order> orders) {
        List<FetchOrdersResponse.OrderDto> ordersDto = orders.stream()
                .map(this::toOrderDto)
                .collect(Collectors.toList());
        return new FetchOrdersResponse(ordersDto);
    }

    public FetchOrdersCommand mapStatus(String status) {
        return new FetchOrdersCommand(Order.Status.valueOf(status.toUpperCase()));
    }

    private FetchOrdersResponse.OrderDto toOrderDto(Order order) {

        List<FetchOrdersResponse.OrderItemDto> items = order.getItems().stream()
                .map(item -> new FetchOrdersResponse.OrderItemDto(
                        item.getProduct().getId().asString(),
                        item.getProduct().getName(),
                        item.getSize().getSize().asString(),
                        item.getStampingName(),
                        item.getStampingNumber()
                ))
                .collect(toList());

        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return FetchOrdersResponse.OrderDto.builder()
                .id(order.getId().asString())
                .items(items)
                .shippingAddress(new ShippingAddressDto(order.getShippingAddress()))
                .status(order.getStatus().name())
                .total(order.getTotal().asString())
                .createdAt(order.getCreatedAt().format(formatter))
                .build();
    }

    private OrderItemCommand toOrderItemCommand(OrderItemDto dto) {
        return OrderItemCommand.builder()
                .productId(dto.getProductId())
                .sizeId(dto.getSizeId())
                .stampingName(dto.getStampingName())
                .stampingNumber(dto.getStampingNumber())
                .build();
    }
}
