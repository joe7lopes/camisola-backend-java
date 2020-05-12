package com.camisola10.camisolabackend.adapter.rest.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
class CreateOrderRequest {
    private List<OrderItemDto> items;
    private ShippingAddressDto shippingAddress;
}
