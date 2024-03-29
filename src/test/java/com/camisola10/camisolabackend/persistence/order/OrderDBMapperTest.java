package com.camisola10.camisolabackend.persistence.order;

import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.Order.OrderId;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.camisola10.camisolabackend.domain.order.Order.Status.PROCESSING;
import static com.camisola10.camisolabackend.domain.order.Order.Status.SHIPPED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderDBMapperTest {

    private OrderDBMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OrderDBMapper();
    }

    @Test
    void shouldMapOrderToOrderDb() {
        var productMock = mock(Product.class);
        var productSizeMock = mock(ProductSize.class);
        var shippingAddressMock = mock(ShippingAddress.class);
        var orderItem =  OrderItem.builder()
                .product(productMock)
                .size(productSizeMock)
                .stampingName("brian")
                .stampingNumber("12")
                .build();
        var items = List.of(orderItem);
        var order = Order.builder()
                .id(OrderId.create("1234"))
                .items(items)
                .shippingAddress(shippingAddressMock)
                .status(PROCESSING)
                .createdAt(LocalDateTime.now())
                .privateNote("note1")
                .build();

        when(productSizeMock.getPrice()).thenReturn(Money.from(23));

        OrderDb orderDb = mapper.map(order);

        assertThat(orderDb.getOrderId()).isEqualTo(order.getId().asString());
        assertThat(orderDb.getItems()).isEqualTo(order.getItems());
        assertThat(orderDb.getShippingAddress()).isEqualTo(order.getShippingAddress());
        assertThat(orderDb.getStatus()).isEqualTo(order.getStatus());
        assertThat(orderDb.getCreatedAt()).isEqualTo(order.getCreatedAt());
        assertThat(orderDb.getTotal()).isEqualTo(order.getTotal().getValue());
        assertThat(orderDb.getPrivateNote()).isEqualTo(order.getPrivateNote());
    }

    @Test
    public void shouldMapFromPageableOrderDBToPageableOrder() {
        var orderId = OrderId.create("1234").asString();
        var product = mock(Product.class);
        var productSize = mock(ProductSize.class);
        var orderItem = OrderItem.builder()
                .product(product)
                .size(productSize)
                .stampingName("name")
                .stampingNumber("number")
                .build();
        var items = List.of(orderItem);
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

        var orderDbPageable = new PageImpl<>(Collections.singletonList(orderDb));

        //WHEN
        var result = mapper.map(orderDbPageable);

        //THEN
        var order = result.getContent().get(0);
        assertThat(order.getId().asString()).isEqualTo(orderDb.getOrderId());
        assertThat(order.getItems()).hasSize(1);
        assertThat(order.getCreatedAt()).isEqualTo(dateNow);
        assertThat(order.getStatus()).isEqualTo(orderDb.getStatus());
    }

    @Test
    public void shouldMapFromOrderDBToOrder() {

        var productMock = mock(Product.class);
        var productSizeMock = mock(ProductSize.class);
        var shippingAddressMock = mock(ShippingAddress.class);
        var orderItem = OrderItem.builder()
                .product(productMock)
                .size(productSizeMock)
                .stampingName("brian")
                .stampingNumber("12")
                .build();

        var items = List.of(orderItem);
        var orderDB = OrderDb.builder()
                .orderId("1234")
                .items(items)
                .shippingAddress(shippingAddressMock)
                .status(PROCESSING)
                .createdAt(LocalDateTime.now())
                .build();

        when(productSizeMock.getPrice()).thenReturn(Money.from(23));

        Order order = mapper.map(orderDB);

        assertThat(order.getId().asString()).isEqualTo(orderDB.getOrderId());
        assertThat(order.getItems()).isEqualTo(orderDB.getItems());
        assertThat(order.getShippingAddress()).isEqualTo(orderDB.getShippingAddress());
        assertThat(order.getStatus()).isEqualTo(orderDB.getStatus());
        assertThat(order.getCreatedAt()).isEqualTo(orderDB.getCreatedAt());
    }

}
