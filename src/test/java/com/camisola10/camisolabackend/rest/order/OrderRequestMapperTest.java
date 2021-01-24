package com.camisola10.camisolabackend.rest.order;

import com.camisola10.camisolabackend.application.port.in.command.order.FetchOrdersByStatusCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderCommand;
import com.camisola10.camisolabackend.domain.EmailAddress;
import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.camisola10.camisolabackend.domain.order.Order.Status.PROCESSING;
import static com.camisola10.camisolabackend.domain.order.Order.Status.SHIPPED;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class OrderRequestMapperTest {

    private OrderRequestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OrderRequestMapper();
    }

    @Test
    void shouldMapFromRequestToCommand() {
        var shippingAddress = ShippingAddressDto.builder()
                .firstName("John")
                .lastName("Due")
                .phone("99223")
                .email("john.due@gmail.com")
                .city("Moscow")
                .postCode("443-23")
                .address("ul.. milan")
                .build();

        var items = List.of(
                new CreateOrderRequest.OrderItem("1", "1", "sergio", "mendonca"),
                new CreateOrderRequest.OrderItem("2", "12"),
                new CreateOrderRequest.OrderItem("1", "2")
        );

        var dto = new CreateOrderRequest(items, shippingAddress);
        var command = mapper.map(dto);

        //TODO assert all values
        assertThat(command.getItems()).hasSize(items.size());

        assertThat(command.getShippingAddress().getFirstName()).isEqualTo(dto.getShippingAddress().getFirstName());
        assertThat(command.getShippingAddress().getLastName()).isEqualTo(dto.getShippingAddress().getLastName());
        assertThat(command.getShippingAddress().getAddress()).isEqualTo(dto.getShippingAddress().getAddress());
        assertThat(command.getShippingAddress().getCity()).isEqualTo(dto.getShippingAddress().getCity());
        assertThat(command.getShippingAddress().getPhone()).isEqualTo(dto.getShippingAddress().getPhone());
        assertThat(command.getShippingAddress().getPostCode()).isEqualTo(dto.getShippingAddress().getPostCode());
        assertThat(command.getShippingAddress().getEmailAddress().asString()).isEqualTo(dto.getShippingAddress().getEmail());
    }

    @Test
    public void shouldMapToUpdateOrderCommand() {
        var orderId = UUID.randomUUID().toString();
        var request = new UpdateOrderRequest(orderId, "PROCESSING", "bla");

        UpdateOrderCommand command = mapper.map(orderId, request);

        assertThat(command.getOrderId().asString()).isEqualTo(orderId);
        assertThat(command.getStatus()).isEqualTo(PROCESSING);
        assertThat(command.getPrivateNote()).isEqualTo("bla");
    }

    @Test
    public void shouldMapToFetchOrdersCommand() {

        FetchOrdersByStatusCommand command = mapper.mapStatus("processing");

        assertThat(command.getStatus()).isEqualTo(PROCESSING);
    }

    @Test
    public void shouldMapFromOrderToResponseDto() {
        var order = createOrder();
        Page<Order> pageableOrder = new PageImpl<>(Collections.singletonList(order));

        //WHEN
        var response = mapper.map(pageableOrder);

        //THEN
        var actual = response.getContent().get(0);
        assertThat(actual.getItems().get(0).productId).isEqualTo(order.getItems().get(0).getProduct().getId().asString());
        assertShippingAddress(actual.shippingAddress, order.getShippingAddress());
        assertItems(actual.getItems().get(0), order.getItems().get(0));
        assertThat(actual.getCreatedAt()).isEqualTo("05/02/2020 02:02");
        assertThat(actual.getStatus()).isEqualTo(order.getStatus().name());
        assertThat(actual.getPrivateNote()).isEqualTo(order.getPrivateNote());
    }

    private void assertItems(OrderDto.OrderItemDto actual, OrderItem expected) {
        assertThat(actual.productId).isEqualTo(expected.getProduct().getId().asString());
        assertThat(actual.productName).isEqualTo(expected.getProduct().getName());
        assertThat(actual.size).isEqualTo(expected.getSize().getSize().asString());
        assertThat(actual.stampingName).isEqualTo(expected.getStampingName());
        assertThat(actual.stampingNumber).isEqualTo(expected.getStampingNumber());
    }

    private void assertShippingAddress(ShippingAddressDto actual, ShippingAddress expected) {
        assertThat(actual.firstName).isEqualTo(expected.getFirstName());
        assertThat(actual.lastName).isEqualTo(expected.getLastName());
        assertThat(actual.email).isEqualTo(expected.getEmailAddress().asString());
        assertThat(actual.phone).isEqualTo(expected.getPhone());
        assertThat(actual.address).isEqualTo(expected.getAddress());
        assertThat(actual.city).isEqualTo(expected.getCity());
        assertThat(actual.postCode).isEqualTo(expected.getPostCode());
    }

    private Order createOrder() {

        var shippingAddress = ShippingAddress.builder()
                .firstName("Fred")
                .lastName("macEnin")
                .address("asdasd.")
                .city("LA")
                .emailAddress(EmailAddress.from("asdad@asd.com"))
                .phone("123")
                .postCode("123")
                .build();


        var product = Product.builder()
                .id(Product.ProductId.create())
                .name("camisolaX")
                .sizes(emptyList())
                .images(emptyList())
                .customizable(false)
                .categories(emptyList())
                .defaultPrice(Money.from("23"))
                .build();

        var size = new ProductSize(
                ProductSize.ProductSizeId.create(),
                new Size("XL"),
                Money.from("23.5"));

        var items = List.of(
                OrderItem.builder()
                        .product(product)
                        .size(size)
                        .stampingName("joe")
                        .stampingNumber("7")
                        .build()
        );

        return Order.builder()
                .id(Order.OrderId.create("1234"))
                .createdAt(LocalDateTime.of(2020, 2, 5, 2, 2))
                .status(SHIPPED)
                .shippingAddress(shippingAddress)
                .items(items)
                .build();
    }
}
