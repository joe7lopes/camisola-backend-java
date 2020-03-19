package com.camisola10.camisolabackend.domain;

import lombok.Value;

@Value
class Address {
    private String street;
    private String city;
    private String postCode;
}
