package com.camisola10.camisolabackend.rest.order;

import com.camisola10.camisolabackend.domain.product.Badge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
class CreateOrderRequest {
    private List<OrderItem> items;
    private ShippingAddressDto shippingAddress;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    static class OrderItem {
        private String productId;
        private String sizeId;
        private String stampingName;
        private String stampingNumber;
        private List<Badge> badges;
    }
}
