package com.camisola10.camisolabackend.adapter.rest.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSizeResponseDto {
    private String id;
    private String size;
    private String price;
}
