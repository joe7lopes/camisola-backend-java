package com.camisola10.camisolabackend.rest.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
class CreateOrderResponse {
    private String orderId;
    private String total;
    private String shippingCost;
}
