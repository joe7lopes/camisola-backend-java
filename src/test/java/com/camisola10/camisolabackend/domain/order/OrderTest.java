package com.camisola10.camisolabackend.domain.order;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.camisola10.camisolabackend.domain.order.Order.Status.PROCESSING;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
            new Order(OrderId.from(null), null, null, null, null);
        });
    }

    @Test
    public void shouldThrowForNullItems() {
        assertThrows(InvalidOrderException.class, () -> {
            new Order(OrderId.create(), null, null, null, null);
        });
    }

    @Test
    public void shouldThrowForEmptyItems() {
        assertThrows(InvalidOrderException.class, () -> {
            new Order(OrderId.create(), null, emptyList(), null, null);
        });
    }

    @Test
    public void shouldHaveCreatedDateTime() {
        var item = mock(OrderItem.class);
        assertThrows(InvalidOrderException.class, () -> {
            new Order(OrderId.create(), null, List.of(item) , null, null);
        });
    }

    @Test
    public void shouldHaveStatus() {
        var item = mock(OrderItem.class);
        InvalidOrderException exception = assertThrows(InvalidOrderException.class, () -> {
            new Order(OrderId.create(), null, List.of(item), LocalDateTime.now(), null);
        });

        assertThat(exception.getMessage()).isEqualTo("An order should have status");
    }

    @Test
    public void shouldHaveShippingAddress() {
        var item = mock(OrderItem.class);
        InvalidOrderException exception = assertThrows(InvalidOrderException.class, () -> {
            new Order(OrderId.create(), null, List.of(item), LocalDateTime.now(), PROCESSING);
        });

        assertThat(exception.getMessage()).isEqualTo("An order should have a shipping address");
    }

    @Test
    public void shouldCalculateOrderTotal() {
        assertTotal("0", "0", "0");
        assertTotal("-1", "-22", "-23");
        assertTotal("53", "99.99", "152.99");
        assertTotal("0", "10023.099", "10023.099");
        assertTotal("0.74", "53.67771", "54.41771");
    }

    private void assertTotal(String item1Value, String item2Value, String total){
        var item1 = mock(OrderItem.class, RETURNS_DEEP_STUBS);
        var item2 = mock(OrderItem.class, RETURNS_DEEP_STUBS);
        var shippingAddress = mock(ShippingAddress.class);
        var order1 = Order.builder()
                .id(OrderId.create())
                .shippingAddress(shippingAddress)
                .status(PROCESSING)
                .items(List.of(item1, item2))
                .createdAt(LocalDateTime.now())
                .build();

        when(item1.getSize().getPrice()).thenReturn(Money.from(item1Value));
        when(item2.getSize().getPrice()).thenReturn(Money.from(item2Value));

        assertThat(order1.getTotal()).isEqualTo(Money.from(total));
    }
}
