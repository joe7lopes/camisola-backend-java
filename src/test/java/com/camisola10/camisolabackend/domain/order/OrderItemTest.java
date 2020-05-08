package com.camisola10.camisolabackend.domain.order;

import com.camisola10.camisolabackend.domain.order.OrderItem.InvalidOrderItemException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderItemTest {

    @Test
    public void shouldThrowForNullProduct() {
        assertThrows(InvalidOrderItemException.class, () -> {
            OrderItem.builder()
                    .build();
        });

    }


    @Test
    public void shouldThrowForNullSize() {
        assertThrows(InvalidOrderItemException.class, () -> {
            OrderItem.builder()
                    .build();
        });

    }
}
