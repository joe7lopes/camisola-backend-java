package com.camisola10.camisolabackend.rest.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
class UpdateOrderRequest {
    private String orderId;
    private String status;
    private String privateNote;
}
