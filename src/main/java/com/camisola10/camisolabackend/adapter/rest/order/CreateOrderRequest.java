package com.camisola10.camisolabackend.adapter.rest.order;

import lombok.AllArgsConstructor;
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
    static class OrderItem {
        private String productId;
        private String sizeId;
        private String stampingName;
        private String stampingNumber;

        public OrderItem(String productId, String sizeId) {
            this.productId = productId;
            this.sizeId = sizeId;
        }
    }
}
