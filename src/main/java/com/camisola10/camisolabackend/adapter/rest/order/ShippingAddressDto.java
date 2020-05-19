package com.camisola10.camisolabackend.adapter.rest.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
class ShippingAddressDto {
    String firstName;
    String lastName;
    String email;
    String phone;
    String address;
    String city;
    String postCode;
}
