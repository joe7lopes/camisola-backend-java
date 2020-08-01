package com.camisola10.camisolabackend.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Money {
    BigDecimal value;

    public static Money from(String value) {
        return new Money(new BigDecimal(value));
    }

    public static Money from(int value) {
        return new Money(new BigDecimal(value));
    }

    public static Money from(BigDecimal value){
        return new Money(value);
    }

    public Money add(Money other){
        return new Money(value.add(other.value));
    }

    public String asString(){
        return value.toPlainString();
    }
}
