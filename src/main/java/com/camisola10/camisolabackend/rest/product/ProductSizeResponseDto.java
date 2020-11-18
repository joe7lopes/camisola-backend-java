package com.camisola10.camisolabackend.rest.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
class ProductSizeResponseDto {
    private String id;
    private String size;
    private String price;
}
