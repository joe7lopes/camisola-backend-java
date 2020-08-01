package com.camisola10.camisolabackend.adapter.persistence.order;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static com.camisola10.camisolabackend.domain.order.Order.Status.PROCESSING;
import static com.camisola10.camisolabackend.domain.order.Order.Status.SHIPPED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderDBMapperTest {

    private OrderDBMapper mapper = Mappers.getMapper(OrderDBMapper.class);

    @Test
    void shouldMapOrderToOrderDb() {
        var productMock = mock(Product.class);
        var productSizeMock = mock(ProductSize.class);
        var shippingAddressMock = mock(ShippingAddress.class);
        var orderItem = new OrderItem(productMock, productSizeMock, "brian", "12");
        var items = List.of(orderItem);
        var order = Order.builder()
                .id(OrderId.create())
                .items(items)
                .shippingAddress(shippingAddressMock)
                .status(PROCESSING)
                .createdAt(LocalDateTime.now())
                .build();

        when(productSizeMock.getPrice()).thenReturn(Money.from(23));

        OrderDb orderDb = mapper.map(order);

        assertThat(orderDb.getOrderId()).isEqualTo(order.getId().asString());
        assertThat(orderDb.getItems()).isEqualTo(order.getItems());
        assertThat(orderDb.getShippingAddress()).isEqualTo(order.getShippingAddress());
        assertThat(orderDb.getStatus()).isEqualTo(order.getStatus());
        assertThat(orderDb.getCreatedAt()).isEqualTo(order.getCreatedAt());
        assertThat(orderDb.getTotal()).isEqualTo(order.getTotal().getValue());
    }

    @Test
    public void shouldMapToOrder() {
        var orderId = OrderId.create().asString();
        var product = mock(Product.class);
        var productSize = mock(ProductSize.class);
        var items = List.of(new OrderItem(product, productSize, "name", "number"));
        var shippingAddress = mock(ShippingAddress.class);
        LocalDateTime dateNow = LocalDateTime.now();

        var orderDb = OrderDb.builder()
                .orderId(orderId)
                .status(SHIPPED)
                .items(items)
                .shippingAddress(shippingAddress)
                .createdAt(dateNow)
                .lastModified(dateNow)
                .build();

        Order order = mapper.map(orderDb);

        assertThat(order.getId().asString()).isEqualTo(orderDb.getOrderId());
        assertThat(order.getItems()).hasSize(1);
        assertThat(order.getCreatedAt()).isEqualTo(dateNow);
        assertThat(order.getStatus()).isEqualTo(orderDb.getStatus());
    }
}
