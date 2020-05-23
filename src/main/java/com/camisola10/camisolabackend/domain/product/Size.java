package com.camisola10.camisolabackend.domain.product;

import lombok.Value;

@Value
public class Size {
    String value;

    public String asString(){
        return value;
    }
}
