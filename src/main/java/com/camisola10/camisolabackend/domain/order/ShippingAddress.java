package com.camisola10.camisolabackend.domain.order;

import com.camisola10.camisolabackend.domain.EmailAddress;
import lombok.Builder;
import lombok.Data;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Builder
@Data
public class ShippingAddress {

    String firstName;
    String lastName;
    EmailAddress emailAddress;
    String phone;
    String address;
    String city;
    String postCode;

    public ShippingAddress(String firstName, String lastName, EmailAddress emailAddress, String phone, String address, String city, String postCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phone = phone;
        this.address = address;
        this.postCode = postCode;
        this.city = city;
        validate();
    }

    private void validate() {
        if (isBlank(phone)) {
            throw new InvalidShippingAddress("Phone number cannot be empty");
        }

        if (isBlank(address) || isBlank(city) || isBlank(postCode)) {
            throw new InvalidShippingAddress("Address or city or post code cannot be empty");
        }

    }

    public static class InvalidShippingAddress extends RuntimeException {
        public InvalidShippingAddress(String message) {
            super(message);
        }
    }
}
