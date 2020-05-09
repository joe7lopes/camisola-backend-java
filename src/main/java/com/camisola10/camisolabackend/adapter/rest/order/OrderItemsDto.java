package com.camisola10.camisolabackend.adapter.rest.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
class OrderItemsDto {
    private String productId;
    private String sizeId;
    private String stampingName;
    private String stampingNumber;
}
