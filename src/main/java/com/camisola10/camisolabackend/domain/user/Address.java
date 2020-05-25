package com.camisola10.camisolabackend.domain.user;

import lombok.Value;

@Value
public
class Address {
    private String street;
    private String city;
    private String postCode;
}
