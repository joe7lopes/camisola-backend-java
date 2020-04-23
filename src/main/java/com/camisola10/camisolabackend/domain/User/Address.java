package com.camisola10.camisolabackend.domain.User;

import lombok.Value;

@Value
class Address {
    private String street;
    private String city;
    private String postCode;
}
