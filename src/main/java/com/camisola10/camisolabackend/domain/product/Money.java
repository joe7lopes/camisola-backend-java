package com.camisola10.camisolabackend.domain.product;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Money {
    BigDecimal value;

    public static Money from(String value) {
        return new Money(new BigDecimal(value));
    }

    public String asString(){
        return value.toPlainString();
    }
}
