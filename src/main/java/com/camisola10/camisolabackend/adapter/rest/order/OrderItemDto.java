package com.camisola10.camisolabackend.adapter.rest.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
class OrderItemDto {
    private String productId;
    private String sizeId;
    private String stampingName;
    private String stampingNumber;

    public OrderItemDto(String productId, String sizeId) {
        this.productId = productId;
        this.sizeId = sizeId;
    }
}
