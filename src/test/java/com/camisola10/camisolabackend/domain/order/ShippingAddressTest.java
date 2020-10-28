package com.camisola10.camisolabackend.domain.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShippingAddressTest {

    @Test
    public void shouldThrowForNullPhoneNumber() {
        ShippingAddress.InvalidShippingAddress exception = assertThrows(ShippingAddress.InvalidShippingAddress.class, () -> ShippingAddress.builder()
                .build());

        assertThat(exception.getMessage()).isEqualTo("Phone number cannot be empty");
    }

    @Test
    public void shouldThrowForEmptyPhoneNumber() {
        ShippingAddress.InvalidShippingAddress exception = assertThrows(ShippingAddress.InvalidShippingAddress.class, () -> ShippingAddress.builder()
                .phone("")
                .build());

        assertThat(exception.getMessage()).isEqualTo("Phone number cannot be empty");
    }

    @Test
    public void shouldThrowForEmptyOrNullAddress() {
        ShippingAddress.InvalidShippingAddress exception = assertThrows(ShippingAddress.InvalidShippingAddress.class, () -> ShippingAddress.builder()
                .phone("233")
                .build());

        assertThat(exception.getMessage()).isEqualTo("Address or city or post code cannot be empty");
    }

    @Test
    public void shouldThrowForEmptyOrNullPostCode() {
        ShippingAddress.InvalidShippingAddress exception = assertThrows(ShippingAddress.InvalidShippingAddress.class, () -> ShippingAddress.builder()
                .phone("233")
                .postCode("")
                .build());

        assertThat(exception.getMessage()).isEqualTo("Address or city or post code cannot be empty");
    }

    @Test
    public void shouldThrowForEmptyOrNullCity() {
        ShippingAddress.InvalidShippingAddress exception = assertThrows(ShippingAddress.InvalidShippingAddress.class, () -> ShippingAddress.builder()
                .phone("233")
                .city("")
                .build());

        assertThat(exception.getMessage()).isEqualTo("Address or city or post code cannot be empty");
    }
}
