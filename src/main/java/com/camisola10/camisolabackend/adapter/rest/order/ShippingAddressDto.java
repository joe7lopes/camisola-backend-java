package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.domain.order.ShippingAddress;
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

    public ShippingAddressDto(ShippingAddress shippingAddress) {
        this.firstName = shippingAddress.getFirstName();
        this.lastName = shippingAddress.getLastName();
        this.email = shippingAddress.getEmail().asString();
        this.phone = shippingAddress.getPhone();
        this.address = shippingAddress.getAddress();
        this.city = shippingAddress.getCity();
        this.postCode = shippingAddress.getPostCode();
    }
}
