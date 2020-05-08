package com.camisola10.camisolabackend.adapter.rest.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
class ProductImageDto {
    private String name;
    //base 64
    private String file;
    private boolean isDefault;

}
