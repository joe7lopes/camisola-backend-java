package com.camisola10.camisolabackend.adapter.rest.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
class CreateOrderRequestDto {
    private List<OrderItemsDto> items;
    private ShippingAddressDto shippingAddress;
}
