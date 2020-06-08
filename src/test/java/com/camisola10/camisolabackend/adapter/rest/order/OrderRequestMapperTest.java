package com.camisola10.camisolabackend.adapter.rest.order;

import com.camisola10.camisolabackend.application.port.in.command.order.FetchOrdersCommand;
import com.camisola10.camisolabackend.application.port.in.command.order.UpdateOrderStatusCommand;
import com.camisola10.camisolabackend.domain.Money;
import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.order.Order;
import com.camisola10.camisolabackend.domain.order.OrderItem;
import com.camisola10.camisolabackend.domain.order.ShippingAddress;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.ProductSize;
import com.camisola10.camisolabackend.domain.product.Size;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.camisola10.camisolabackend.domain.order.Order.Status.PROCESSING;
import static com.camisola10.camisolabackend.domain.order.Order.Status.SHIPPED;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class OrderRequestMapperTest {

    private OrderRequestMapper mapper = Mappers.getMapper(OrderRequestMapper.class);

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
                new OrderItemDto("1", "1", "sergio", "mendonca"),
                new OrderItemDto("2", "12"),
                new OrderItemDto("1", "2")
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
        assertThat(command.getShippingAddress().getEmail().asString()).isEqualTo(dto.getShippingAddress().getEmail());
    }

    @Test
    void testMapStringToEmail() {
        var email = "jj@ll.com";

        Email result = mapper.map(email);

        assertThat(result.asString()).isEqualTo(email);
    }

    @Test
    public void shouldMapToUpdateStatusCommand() {
        var orderId = UUID.randomUUID().toString();
        var request = new UpdateOrderStatusRequest("PROCESSING");

        UpdateOrderStatusCommand command = mapper.map(orderId, request);

        assertThat(command.getOrderId().asString()).isEqualTo(orderId);
        assertThat(command.getStatus()).isEqualTo(PROCESSING);
    }

    @Test
    public void shouldMapToFetchOrdersCommand() {

        FetchOrdersCommand command = mapper.mapStatus("processing");

        assertThat(command.getStatus()).isEqualTo(PROCESSING);
    }

    @Test
    public void shouldMapFromOrderToFetchOrdersResponse() {
        var order = createOrder();

        FetchOrdersResponse response = mapper.map(Collections.singletonList(order));

        FetchOrdersResponse.OrderDto actual = response.getOrders().get(0);
        assertThat(actual.getItems().get(0).productId).isEqualTo(order.getItems().get(0).getProduct().getId().asString());
        assertShippingAddress(actual.shippingAddress, order.getShippingAddress());
        assertItems(actual.items.get(0), order.getItems().get(0));
        assertThat(actual.getCreatedAt()).isEqualTo("05/02/2020 02:02");
        assertThat(actual.getStatus()).isEqualTo(order.getStatus().name());
    }

    private void assertItems(FetchOrdersResponse.OrderItemDto actual, OrderItem expected) {
        assertThat(actual.productId).isEqualTo(expected.getProduct().getId().asString());
        assertThat(actual.productName).isEqualTo(expected.getProduct().getName());
        assertThat(actual.size).isEqualTo(expected.getSize().getSize().asString());
        assertThat(actual.stampingName).isEqualTo(expected.getStampingName());
        assertThat(actual.stampingNumber).isEqualTo(expected.getStampingNumber());
    }

    private void assertShippingAddress(ShippingAddressDto actual, ShippingAddress expected){
        assertThat(actual.firstName).isEqualTo(expected.getFirstName());
        assertThat(actual.lastName).isEqualTo(expected.getLastName());
        assertThat(actual.email).isEqualTo(expected.getEmail().asString());
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
                .email(Email.from("asdad@asd.com"))
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
                .id(Order.OrderId.create())
                .createdAt(LocalDateTime.of(2020, 2, 5, 2, 2))
                .status(SHIPPED)
                .shippingAddress(shippingAddress)
                .items(items)
                .build();
    }
}
