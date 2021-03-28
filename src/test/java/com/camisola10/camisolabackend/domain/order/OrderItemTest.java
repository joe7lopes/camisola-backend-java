package com.camisola10.camisolabackend.domain.order;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.order.OrderItem.InvalidOrderItemException;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderItemTest {

    @Test
    public void shouldThrowForNullProduct() {
        assertThrows(InvalidOrderItemException.class, () -> OrderItem.builder()
                .build());

    }


    @Test
    public void shouldThrowForNullSize() {
        assertThrows(InvalidOrderItemException.class, () -> OrderItem.builder()
                .build());

    }

    @Test
    public void shouldReturnPriceWithoutStampingCost() {
        var p = mock(Product.class);
        var pSize = mock(ProductSize.class);
        Money productPrice = Money.from(33);
        when(pSize.getPrice()).thenReturn(productPrice);

        var item = OrderItem.builder()
                .product(p)
                .size(pSize)
                .stampingName(null)
                .stampingNumber(null)
                .build();

        assertThat(item.getPrice()).isEqualTo(productPrice);
    }

    @Test
    public void shouldReturnPriceWithoutStampingCostForBlankNames() {
        var p = mock(Product.class);
        var pSize = mock(ProductSize.class);
        Money productPrice = Money.from(33);
        when(pSize.getPrice()).thenReturn(productPrice);

        var item = OrderItem.builder()
                .product(p)
                .size(pSize)
                .stampingName("")
                .stampingNumber("")
                .build();

        assertThat(item.getPrice()).isEqualTo(productPrice);
    }

    @Test
    public void shouldReturnPriceWithStampingCostWhenNameIsFilled() {
        var p = mock(Product.class);
        var pSize = mock(ProductSize.class);
        Money productPrice = Money.from(33);
        when(pSize.getPrice()).thenReturn(productPrice);

        var item = OrderItem.builder()
                .product(p)
                .size(pSize)
                .stampingName("name")
                .stampingNumber("12")
                .build();

        assertThat(item.getPrice()).isEqualTo(productPrice.add(OrderItem.STAMPING_COST));
    }

    @Test
    public void shouldReturnPriceWithStampingCostWhenNumberIsFilled() {
        var p = mock(Product.class);
        var pSize = mock(ProductSize.class);
        Money productPrice = Money.from(33);
        when(pSize.getPrice()).thenReturn(productPrice);

        var item = OrderItem.builder()
                .product(p)
                .size(pSize)
                .stampingName("")
                .stampingNumber("12")
                .build();

        assertThat(item.getPrice()).isEqualTo(productPrice.add(OrderItem.STAMPING_COST));
    }
}
