package com.camisola10.camisolabackend.adapter.rest.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
class OrderDto {
    private String id;
    private ShippingAddressDto shippingAddress;
    private List<OrderItemDto> items;
    private LocalDateTime createdAt;
    private String status;
}
