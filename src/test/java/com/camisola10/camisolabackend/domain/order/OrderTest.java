package com.camisola10.camisolabackend.domain.order;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.camisola10.camisolabackend.domain.order.Order.Status.PROCESSING;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class OrderTest {

    @Test
    public void shouldThrowForNullId() {
        assertThrows(InvalidOrderIdException.class, () -> {
             new Order(null, null, null, null, null);
        });
    }

    @Test
    public void shouldThrowForEmptyId() {
        assertThrows(InvalidOrderIdException.class, () -> {
            new Order(new Order.OrderId(null), null, null, null, null);
        });
    }

    @Test
    public void shouldThrowForNullItems() {
        assertThrows(InvalidOrderException.class, () -> {
            new Order(Order.OrderId.create(), null, null, null, null);
        });
    }

    @Test
    public void shouldThrowForEmptyItems() {
        assertThrows(InvalidOrderException.class, () -> {
            new Order(Order.OrderId.create(), null, emptyList(), null, null);
        });
    }

    @Test
    public void shouldHaveCreatedDateTime() {
        var item = mock(OrderItem.class);
        assertThrows(InvalidOrderException.class, () -> {
            new Order(Order.OrderId.create(), null, List.of(item) , null, null);
        });
    }

    @Test
    public void shouldHaveStatus() {
        var item = mock(OrderItem.class);
        InvalidOrderException exception = assertThrows(InvalidOrderException.class, () -> {
            new Order(Order.OrderId.create(), null, List.of(item), LocalDateTime.now(), null);
        });

        assertThat(exception.getMessage()).isEqualTo("An order should have status");
    }

    @Test
    public void shouldHaveShippingAddress() {
        var item = mock(OrderItem.class);
        InvalidOrderException exception = assertThrows(InvalidOrderException.class, () -> {
            new Order(Order.OrderId.create(), null, List.of(item), LocalDateTime.now(), PROCESSING);
        });

        assertThat(exception.getMessage()).isEqualTo("An order should have a shipping address");
    }
}
