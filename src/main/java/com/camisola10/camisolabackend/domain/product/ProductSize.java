package com.camisola10.camisolabackend.domain.product;

import com.camisola10.camisolabackend.domain.Money;
import lombok.Value;

@Value
public class ProductSize {
    Size size;
    Money price;
}
