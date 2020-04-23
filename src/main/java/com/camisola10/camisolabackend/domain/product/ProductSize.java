package com.camisola10.camisolabackend.domain.product;

import lombok.Value;

@Value
public class ProductSize {
    Size size;
    Money price;
}
