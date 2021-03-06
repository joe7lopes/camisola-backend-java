package com.camisola10.camisolabackend.rest.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
class OrderDto {
    String id;
    ShippingAddressDto shippingAddress;
    List<OrderItemDto> items;
    String createdAt;
    String privateNote;
    String status;
    String total;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class OrderItemDto {
        String productId;
        String productName;
        String size;
        String stampingName;
        String stampingNumber;
    }
}




